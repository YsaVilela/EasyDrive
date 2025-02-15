package br.com.fatec.easyDrive.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fatec.easyDrive.DTO.reserva.DadosDetalhamentoReserva;
import br.com.fatec.easyDrive.DTO.reserva.DadosReserva;
import br.com.fatec.easyDrive.entity.Cliente;
import br.com.fatec.easyDrive.entity.Reserva;
import br.com.fatec.easyDrive.entity.ServicoReserva;
import br.com.fatec.easyDrive.entity.Veiculo;
import br.com.fatec.easyDrive.enumerator.ServicoEnum;
import br.com.fatec.easyDrive.enumerator.StatusEnum;
import br.com.fatec.easyDrive.exception.InvalidDataException;
import br.com.fatec.easyDrive.exception.NotFoundException;
import br.com.fatec.easyDrive.repository.ClienteRepository;
import br.com.fatec.easyDrive.repository.ReservaRepository;
import br.com.fatec.easyDrive.repository.ServicoReservaRepository;
import br.com.fatec.easyDrive.repository.VeiculoRepository;
import jakarta.validation.Valid;

@Service
public class ReservaService {
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private ServicoReservaRepository servicoReservaRepository;
	
	public DadosDetalhamentoReserva gerarOrcamento(@Valid DadosReserva dados) {
		Cliente cliente = clienteRepository.findById(dados.fkCliente()).orElseThrow(() -> 
			new NotFoundException("Cliente com id " + dados.fkCliente() + " não encontrado")
		);
	
		Veiculo veiculo = veiculoRepository.findById(dados.fkVeiculo()).orElseThrow(() -> 
			new NotFoundException("Veiculo com id " + dados.fkVeiculo() + " não encontrado.")
		);
		
		validarDatas(dados.dataInicio(), dados.dataPrevistaFim());
		
		Long diasDeReserva = ChronoUnit.DAYS.between(dados.dataInicio(), (dados.dataPrevistaFim())) + 1;
		Double valorReserva = calcularValorReserva(dados, veiculo, diasDeReserva);
		
		Reserva reserva = new Reserva();
		reserva.setDataInicio(dados.dataInicio());
		reserva.setDataPrevistaFim(dados.dataPrevistaFim());
		reserva.setOrcamento(valorReserva);
		reserva.setOrcamentoFinal(aplicarDesconto(valorReserva, cliente));	
		reserva.setCliente(cliente);
		reserva.setVeiculo(veiculo);
		
		return new DadosDetalhamentoReserva(reserva, dados.servicos());

	}
	
	public DadosDetalhamentoReserva cadastrar(@Valid DadosReserva dados) {

		Cliente cliente = clienteRepository.findById(dados.fkCliente()).orElseThrow(() -> 
			new NotFoundException("Cliente com id " + dados.fkCliente() + " não encontrado")
		);
		
		Veiculo veiculo = veiculoRepository.findById(dados.fkVeiculo()).orElseThrow(() -> 
			new NotFoundException("Veiculo com id " + dados.fkVeiculo() + " não encontrado.")
		);
				
		validarDatas(dados.dataInicio(), dados.dataPrevistaFim());
		validarCliente(cliente);
		validarVeiculo(veiculo);
		validarDisponibilidade(dados);
		
		Reserva reserva = new Reserva();
		Long diasDeReserva = ChronoUnit.DAYS.between(dados.dataInicio(), (dados.dataPrevistaFim())) + 1;
		Double valorReserva = calcularValorReserva(dados, veiculo, diasDeReserva);

		reserva.setDataInicio(dados.dataInicio());
		reserva.setDataPrevistaFim(dados.dataPrevistaFim());
		reserva.setOrcamento(valorReserva);
		reserva.setOrcamentoFinal(aplicarDesconto(valorReserva, cliente));	
		reserva.setStatus(StatusEnum.CADASTRADO);
		reserva.setCliente(cliente);
		reserva.setVeiculo(veiculo);
		reservaRepository.save(reserva);
		
		dados.servicos().forEach(s -> {
				ServicoReserva servicoReserva = new ServicoReserva();
				servicoReserva.setNome(s);
				servicoReserva.setReserva(reserva);
				servicoReserva.setValor(calcularValorServico(s, diasDeReserva, veiculo.getValorDiaria()));
				servicoReservaRepository.save(servicoReserva);
			}
		);
		
		return new DadosDetalhamentoReserva(reserva, dados.servicos());
	}
	
	public void validarCliente(Cliente cliente) {
		if(cliente.getValidadeCNH().isBefore(LocalDate.now())) {
			throw new InvalidDataException ("Não é possivel realizar uma reserva com a Carteira Nacional de Habilitação(CNH) vencida");		
		}
		
		Optional<Reserva> reserva =  reservaRepository.findByClienteIdAndReservaStatus(cliente.getId(), StatusEnum.AGUARDANDO_PAGAMENTO);
		if(reserva.isPresent()) {
			throw new InvalidDataException ("É necessario quitar os pagamentos pendendes para poder realizar uma nova reserva");		
		} 
	}
	
	public void validarVeiculo(Veiculo veiculo) {
		if(veiculo.getStatus() != StatusEnum.ATIVO) {
			throw new InvalidDataException ("Veículo não está ativo para reservas");		
		}
	}
	
	public Double calcularValorReserva(DadosReserva dados, Veiculo veiculo, Long diasDeReserva) {
		Double valorTotalServicos = calcularValorServicos(dados.servicos(), diasDeReserva, veiculo.getValorDiaria());
		Double valorTotalReserva = diasDeReserva*veiculo.getValorDiaria();
		
		return valorTotalReserva + valorTotalServicos;
	}
	
	public Double calcularValorServicos(List<ServicoEnum> servicos, Long diasDeReserva, Double valorDiaria) {
	    return servicos.stream()
	        .mapToDouble(s -> calcularValorServico(s, diasDeReserva, valorDiaria))
	        .sum();
	}
	
	public Double calcularValorServico(ServicoEnum servico, Long diasDeReserva, Double valorDiaria) {
		return valorDiaria * (valorDiaria * servico.getValor()/100);
	}

	
	public Double aplicarDesconto(Double valorTotal, Cliente cliente) {
		Double valorComDesconto = valorTotal-((valorTotal*(cliente.getPlanoAssinatura().getDesconto())/100));
		
		return valorComDesconto;
	}
	
	public void validarDatas(LocalDateTime dataInicio, LocalDateTime dataFinal){	
		if(dataInicio.isAfter(dataFinal)) {
			throw new InvalidDataException ("Data início da reserva não pode ser após data final");		
		}
	}
	
	public void validarDisponibilidade(DadosReserva dados) {
	    Map<StatusEnum, String> mensagensErroCliente = Map.of(
	        StatusEnum.APROVADO, "Já possui uma reserva agendada para a data selecionada",
	        StatusEnum.EM_ANDAMENTO, "Já possui uma reserva em andamento para a data selecionada",
	        StatusEnum.CADASTRADO, "Já possui uma reserva cadastrada para a data selecionada"
	    );

	    Map<StatusEnum, String> mensagensErroVeiculo = Map.of(
	        StatusEnum.CADASTRADO, "O veículo já possui uma reserva cadastrada para a data selecionada",
	        StatusEnum.APROVADO, "O veículo possui uma reserva agendada para a data selecionada",
	        StatusEnum.EM_ANDAMENTO, "O veículo possui uma reserva em andamento para a data selecionada"
	    );

	    validarReserva(dados.fkCliente(), mensagensErroCliente, true, dados);

	    validarReserva(dados.fkVeiculo(), mensagensErroVeiculo, false, dados);
	}

	private void validarReserva(Long id, Map<StatusEnum, String> mensagensErro, boolean isCliente, DadosReserva dados) {
	    for (Map.Entry<StatusEnum, String> entry : mensagensErro.entrySet()) {
	        StatusEnum status = entry.getKey();
	        String mensagemErro = entry.getValue();

	        Optional<Reserva> reserva = isCliente
	            ? reservaRepository.findByClienteIdAndReservaStatusAndData(id, status, dados.dataInicio(), dados.dataPrevistaFim())
	            : reservaRepository.findByVeiculoIdAndReservaStatusAndData(id, status, dados.dataInicio(), dados.dataPrevistaFim());

	        if (reserva.isPresent()) {
	            throw new InvalidDataException(mensagemErro);
	        }
	    }
	}
	
}
