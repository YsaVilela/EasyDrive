package br.com.fatec.easyDrive.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fatec.easyDrive.DTO.pagamento.DadosPagamento;
import br.com.fatec.easyDrive.DTO.reserva.DadosDatalhamentoServicoReserva;
import br.com.fatec.easyDrive.DTO.reserva.DadosDetalhamentoReserva;
import br.com.fatec.easyDrive.DTO.reserva.DadosReserva;
import br.com.fatec.easyDrive.DTO.reserva.DadosResumoReserva;
import br.com.fatec.easyDrive.entity.Cliente;
import br.com.fatec.easyDrive.entity.Pagamento;
import br.com.fatec.easyDrive.entity.Reserva;
import br.com.fatec.easyDrive.entity.ServicoReserva;
import br.com.fatec.easyDrive.entity.Veiculo;
import br.com.fatec.easyDrive.enumerator.ServicoEnum;
import br.com.fatec.easyDrive.enumerator.StatusEnum;
import br.com.fatec.easyDrive.exception.InvalidDataException;
import br.com.fatec.easyDrive.exception.NotFoundException;
import br.com.fatec.easyDrive.repository.ClienteRepository;
import br.com.fatec.easyDrive.repository.PagamentoRepository;
import br.com.fatec.easyDrive.repository.ReservaRepository;
import br.com.fatec.easyDrive.repository.ServicoReservaRepository;
import br.com.fatec.easyDrive.repository.VeiculoRepository;

@Service
public class ReservaService {
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private VeiculoService veiculoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ServicoReservaRepository servicoReservaRepository;
	
	public DadosDetalhamentoReserva gerarOrcamento(@Valid DadosReserva dados) {
		Cliente cliente = clienteRepository.findById(dados.fkCliente()).orElseThrow(() -> 
			new NotFoundException("Cliente com id " + dados.fkCliente() + " não encontrado")
		);
	
		Veiculo veiculo = veiculoRepository.findById(dados.fkVeiculo()).orElseThrow(() -> 
			new NotFoundException("Veiculo com id " + dados.fkVeiculo() + " não encontrado.")
		);
		
		validarDatas(dados.dataInicio(), dados.dataFim());
		
		Long diasDeReserva = ChronoUnit.DAYS.between(dados.dataInicio(), (dados.dataFim())) + 1;
		Double valorReserva = calcularValorReserva(dados, veiculo, diasDeReserva);
		
		Reserva reserva = new Reserva();
		reserva.setDataInicio(dados.dataInicio());
		reserva.setDataFim(dados.dataFim());
		reserva.setOrcamento(valorReserva);
		reserva.setOrcamentoFinal(aplicarDesconto(valorReserva, cliente));	
		reserva.setCliente(cliente);
		reserva.setVeiculo(veiculo);
		reserva.setStatus(StatusEnum.ORCAMENTO);
		
		List<DadosDatalhamentoServicoReserva> servicos = dados.servicos().stream()
			    .map(s -> {
			        ServicoReserva servicoReserva = new ServicoReserva();
			        servicoReserva.setNome(s);
			        servicoReserva.setReserva(reserva);
			        servicoReserva.setValor(calcularValorServico(s, diasDeReserva, veiculo.getValorDiaria()));
			        return new DadosDatalhamentoServicoReserva(servicoReserva);
			    })
			    .collect(Collectors.toList());

		return new DadosDetalhamentoReserva(reserva, servicos);

	}
	
	public DadosDetalhamentoReserva cadastrar(@Valid DadosReserva dados) {

		Cliente cliente = clienteRepository.findById(dados.fkCliente()).orElseThrow(() -> 
			new NotFoundException("Cliente com id " + dados.fkCliente() + " não encontrado")
		);
		
		Veiculo veiculo = veiculoRepository.findById(dados.fkVeiculo()).orElseThrow(() -> 
			new NotFoundException("Veiculo com id " + dados.fkVeiculo() + " não encontrado.")
		);
				
		validarDatas(dados.dataInicio(), dados.dataFim());
		validarCliente(cliente);
		validarVeiculo(veiculo);
		validarDisponibilidade(dados);
		
		Reserva reserva = new Reserva();
		Long diasDeReserva = ChronoUnit.DAYS.between(dados.dataInicio(), (dados.dataFim())) + 1;
		Double valorReserva = calcularValorReserva(dados, veiculo, diasDeReserva);

		reserva.setDataInicio(dados.dataInicio());
		reserva.setDataFim(dados.dataFim());
		reserva.setOrcamento(valorReserva);
		reserva.setOrcamentoFinal(aplicarDesconto(valorReserva, cliente));	
		reserva.setStatus(StatusEnum.CADASTRADO);
		reserva.setCliente(cliente);
		reserva.setVeiculo(veiculo);
		reserva.setValorPago(0.0);
		reserva.setValorFinal(0.0);
		reservaRepository.save(reserva);
		
		dados.servicos().forEach(s -> {
				ServicoReserva servicoReserva = new ServicoReserva();
				servicoReserva.setNome(s);
				servicoReserva.setReserva(reserva);
				servicoReserva.setValor(calcularValorServico(s, diasDeReserva, veiculo.getValorDiaria()));
				servicoReservaRepository.save(servicoReserva);
			}
		);
		
		List<DadosDatalhamentoServicoReserva> servicos = buscarServicos(reserva.getId());
		
		return new DadosDetalhamentoReserva(reserva, servicos);
	}
	
	public DadosDetalhamentoReserva retirada(Long idReserva) {
		Reserva reserva = reservaRepository.findById(idReserva).orElseThrow(() -> 
			new NotFoundException("Reserva com id " + idReserva + " não encontrada")
		);
		
		reserva.setDataRetirada(LocalDateTime.now());
		reserva.setStatus(StatusEnum.EM_ANDAMENTO);
		reservaRepository.save(reserva);

		List<DadosDatalhamentoServicoReserva> servicos = buscarServicos(idReserva);
		
		return new DadosDetalhamentoReserva(reserva, servicos);
	}
	
	public DadosDetalhamentoReserva devolucao(Long idReserva) {
		Reserva reserva = reservaRepository.findById(idReserva).orElseThrow(() -> 
			new NotFoundException("Reserva com id " + idReserva + " não encontrada")
		);
		
		reserva.setDataDevolucao(LocalDateTime.now());
		reserva.setStatus(StatusEnum.AGUARDANDO_PAGAMENTO);
		reserva.setValorFinal(reserva.getOrcamentoFinal());
		reservaRepository.save(reserva);
		
		List<DadosDatalhamentoServicoReserva> servicos = buscarServicos(idReserva);
		
		return new DadosDetalhamentoReserva(reserva, servicos);
	}
	
	public DadosDetalhamentoReserva pagamento(DadosPagamento dados) {
		Reserva reserva = reservaRepository.findByReservaIdAndStatus(dados.idReserva(), StatusEnum.AGUARDANDO_PAGAMENTO).orElseThrow(() -> 
			new NotFoundException("Reserva com id " + dados.idReserva() + " aguardando pagamento não encontrada")
		);
		
		if(reserva.getValorPago() + dados.valor() > reserva.getValorFinal()) {
			throw new InvalidDataException ("O valor total não pode ultrapassar o valor da reserva");		
		}
		
		Pagamento pagamento = new Pagamento();
		pagamento.setDataPagamento(LocalDateTime.now());
		pagamento.setFormaPagamento(dados.formaPagamento());
		pagamento.setValor(dados.valor());
		pagamento.setReserva(reserva);
		pagamentoRepository.save(pagamento);
		
		reserva.setValorPago(reserva.getValorPago() + dados.valor());
		reservaRepository.save(reserva);
		
		if(reserva.getValorPago().equals(reserva.getValorFinal())) {
			reserva.setStatus(StatusEnum.ENCERRADO);
			veiculoService.alterarStatus(reserva.getVeiculo(), StatusEnum.EM_ANALISE);
			clienteService.atualizarPlano((long)(reserva.getOrcamentoFinal()/3), reserva.getCliente().getId());
		}

		List<DadosDatalhamentoServicoReserva> servicos = buscarServicos(dados.idReserva());
		
		return new DadosDetalhamentoReserva(reserva, servicos);
	}
	
	public void finalizar(Long idReserva, Boolean veiculoBoasCondicoes, Long quilometragem) {
		Reserva reserva = reservaRepository.findByReservaIdAndStatus(idReserva, StatusEnum.ENCERRADO).orElseThrow(() -> 
			new NotFoundException("Reserva com id " + idReserva + " encerrada não encontrada.")
		);
		
		StatusEnum status = veiculoBoasCondicoes ? StatusEnum.ATIVO : StatusEnum.EM_MANUTENCAO;
		veiculoService.alterarStatus(reserva.getVeiculo(), status);
		veiculoService.atualizarQuilometragem(reserva.getVeiculo(), quilometragem);
		reserva.setStatus(StatusEnum.FINALIZADO);
	}
	
	public Page<DadosResumoReserva> buscarReservasCliente(Long idCliente, Pageable paginacao) {
		Page<Reserva> reservas = reservaRepository.findAllByClienteId(idCliente, paginacao);
		
		return reservas.map(reserva -> new DadosResumoReserva(reserva));
	}
	
	public Page<DadosResumoReserva> buscarReservasVeiculo(Long idVeiculo, Pageable paginacao) {
		Page<Reserva> reservas = reservaRepository.findAllByVeiculoId(idVeiculo, paginacao);
		
		return reservas.map(reserva -> new DadosResumoReserva(reserva));
	}
	
	public void validarCliente(Cliente cliente) {
		if(Objects.isNull(cliente.getNumeroCNH()) || Objects.isNull(cliente.getValidadeCNH())) {
			throw new InvalidDataException ("Para realizar uma reserva é obrigatório o cadastro completo da Carteira Nacional de Habilitação(CNH).");		
		}
		
		if(cliente.getValidadeCNH().isBefore(LocalDate.now())) {
			throw new InvalidDataException ("Não é possivel realizar uma reserva com a Carteira Nacional de Habilitação(CNH) vencida");		
		}
		
		if (cliente.getPessoa().getDataDeNascimento().isAfter(LocalDate.now().minusYears(18))) {
		    throw new InvalidDataException("É necessário ter mais de 18 anos para realizar a reserva");
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
	            ? reservaRepository.findByClienteIdAndReservaStatusAndData(id, status, dados.dataInicio(), dados.dataFim())
	            : reservaRepository.findByVeiculoIdAndReservaStatusAndData(id, status, dados.dataInicio(), dados.dataFim());

	        if (reserva.isPresent()) {
	            throw new InvalidDataException(mensagemErro);
	        }
	    }
	}
	
	private List<DadosDatalhamentoServicoReserva> buscarServicos(Long idReserva){
		List<ServicoReserva> servicoReserva = servicoReservaRepository.findByReservaId(idReserva);
		List<DadosDatalhamentoServicoReserva> servicos = servicoReserva.stream()
			    .map(servico -> new DadosDatalhamentoServicoReserva(servico))
			    .collect(Collectors.toList());
		
		return servicos;
	}
}
