package br.com.fatec.easyDrive.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fatec.easyDrive.DTO.veiculo.DadosDetalhamentoVeiculo;
import br.com.fatec.easyDrive.DTO.veiculo.DadosVeiculo;
import br.com.fatec.easyDrive.entity.Reserva;
import br.com.fatec.easyDrive.entity.Veiculo;
import br.com.fatec.easyDrive.enumerator.StatusEnum;
import br.com.fatec.easyDrive.exception.InvalidDataException;
import br.com.fatec.easyDrive.exception.NotFoundException;
import br.com.fatec.easyDrive.repository.ReservaRepository;
import br.com.fatec.easyDrive.repository.VeiculoRepository;
import jakarta.validation.Valid;

@Service
public class VeiculoService {
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	public DadosDetalhamentoVeiculo cadastrar(DadosVeiculo dados) {
		verificarVeiculoJaCadastrado(dados);
		
		Veiculo veiculo = new Veiculo();
		veiculo.setPlaca(dados.placa());
		veiculo.setModelo(dados.modelo());
		veiculo.setMarca(dados.marca());
		veiculo.setAno(dados.ano());
		veiculo.setQuilometragem(dados.quilometragem());
		veiculo.setCategoria(dados.categoria());
		veiculo.setCor(dados.cor());
		veiculo.setTipoCombustivel(dados.tipoCombustivel());
		veiculo.setValorDiaria(dados.valorDiaria());
		veiculo.setStatus(StatusEnum.EM_ANALISE);
		
		veiculoRepository.save(veiculo);
		
		return new DadosDetalhamentoVeiculo(veiculo);
	}
	
	public DadosDetalhamentoVeiculo buscarPorId(Long idVeiculo) {
		Veiculo veiculo = veiculoRepository.findById(idVeiculo).orElseThrow(() -> 
			new NotFoundException("Veículo com id " + idVeiculo + " não encontrado.")
		);
		
		return new DadosDetalhamentoVeiculo(veiculo);
	}
	
	public DadosDetalhamentoVeiculo buscarPorPlaca(String placa) {
		Veiculo veiculo = veiculoRepository.findByPlaca(placa).orElseThrow(() -> 
			new NotFoundException("Veículo com palca " + placa + " não encontrado.")
		);
		
		return new DadosDetalhamentoVeiculo(veiculo);
	}
	
	public Page<DadosDetalhamentoVeiculo> listarTodos(Pageable paginacao) {
		return veiculoRepository.findAll(paginacao).map(DadosDetalhamentoVeiculo::new);
	}
	
	public DadosDetalhamentoVeiculo atualizar(@Valid DadosVeiculo dados, Long idVeiculo) {	
		verificarVeiculoJaCadastradoAtualizacao(dados, idVeiculo);
		
		Veiculo veiculo = veiculoRepository.findById(idVeiculo).orElseThrow(() -> 
			new NotFoundException("Veículo com id " + idVeiculo + " não encontrado")
		);
		
		Optional<Reserva> reserva =  reservaRepository.findByVeiculoIdAndReservaStatus(idVeiculo, StatusEnum.EM_ANDAMENTO);
		
		reserva.ifPresent(r -> { 
		    throw new NotFoundException("Veículo " + idVeiculo + " não pode ser atualizado pois está presente em uma reserva ativa."); 
		});

		veiculo.setPlaca(dados.placa());
		veiculo.setModelo(dados.modelo());
		veiculo.setMarca(dados.marca());
		veiculo.setAno(dados.ano());
		veiculo.setQuilometragem(dados.quilometragem());
		veiculo.setCategoria(dados.categoria());
		veiculo.setCor(dados.cor());
		veiculo.setTipoCombustivel(dados.tipoCombustivel());
		veiculo.setValorDiaria(dados.valorDiaria());
		veiculo.setStatus(StatusEnum.EM_ANALISE);
		
		veiculoRepository.save(veiculo);
		
		return new DadosDetalhamentoVeiculo(veiculo);
	}

	public DadosDetalhamentoVeiculo suspender(Long id) {
		Veiculo veiculo = veiculoRepository.findById(id).orElseThrow(() -> 
			new NotFoundException("Veículo com id " + id + " não encontrado")
		);
		
		alterarStatus(veiculo, StatusEnum.SUSPENSO);

		return new DadosDetalhamentoVeiculo(veiculo);
	}
	
	public DadosDetalhamentoVeiculo ativar(Long id) {
		Veiculo veiculo  = veiculoRepository.findById(id).orElseThrow(() -> 
			new NotFoundException("Veículo com id " + id + " não encontrado")
		);
		
		alterarStatus(veiculo, StatusEnum.ATIVO);

		return new DadosDetalhamentoVeiculo(veiculo);
	}
	
	public void deletar(Long id) {
		Veiculo veiculo  = veiculoRepository.findById(id).orElseThrow(() -> 
			new NotFoundException("Veículo com id " + id + " não encontrado")
		);
		
		List<Reserva> reservas = reservaRepository.findByVeiculoId(id);
		if(!reservas.isEmpty()) {
			throw new InvalidDataException ("Não é possivel excluir veiculo que já participou de uma reserva");		
		}
		veiculoRepository.delete(veiculo);
	}
	
	public void alterarStatus(Veiculo veiculo, StatusEnum status) {
		veiculo.setStatus(status);
		veiculoRepository.save(veiculo);
	}
	
	public void atualizarQuilometragem(Veiculo veiculo, Long quilometragem) {
		if (veiculo.getQuilometragem() >= quilometragem) {  
		    throw new InvalidDataException("A quilometragem não pode ser menor que a do início da reserva.");  
		}
		
		veiculo.setQuilometragem(quilometragem);
		veiculoRepository.save(veiculo);
	}
	
	public void verificarVeiculoJaCadastrado(DadosVeiculo dados) {
		veiculoRepository.findByPlaca(dados.placa())
		    .ifPresent(cliente -> {
		        throw new InvalidDataException("Veiculo com placa " + dados.placa() + " já cadastrado");
		    });
	}
	
	public void verificarVeiculoJaCadastradoAtualizacao(DadosVeiculo dados, Long idVeiculo) {
		veiculoRepository.findByPlaca(dados.placa())
		    .filter(c -> !c.getId().equals(idVeiculo))
		    .ifPresent(c -> { throw new InvalidDataException("Veiculo com placa " + dados.placa() + " já cadastrado"); });
	}
}
