package com.guilherme.controlefinanceiro.Nyxhein.service;

import com.guilherme.controlefinanceiro.Nyxhein.entity.Categoria;
import com.guilherme.controlefinanceiro.Nyxhein.repository.CategoriaRepository;
import com.guilherme.controlefinanceiro.Nyxhein.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final TransacaoRepository transacaoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, TransacaoRepository transacaoRepository){
        this.transacaoRepository = transacaoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodas(){
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com id: " + id));
    }

    public Categoria criar(Categoria categoria) {
        boolean jaExiste = !categoriaRepository
                .findByNomeContainingIgnoreCase(categoria.getNome())
                .isEmpty();

        if (jaExiste) {
            throw new RuntimeException("Já existe uma categoria com esse nome");
        }

        categoria.setCriadoEm(LocalDateTime.now());
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Long id, Categoria dadosAtualizados) {
        Categoria categoriaExistente = buscarPorId(id);

        boolean outroComMesmoNome = categoriaRepository
                .findByNomeContainingIgnoreCase(dadosAtualizados.getNome())
                .stream()
                .anyMatch(c -> !c.getId().equals(id));

        if (outroComMesmoNome) {
            throw new RuntimeException("Já existe outra categoria com esse nome");
        }

        categoriaExistente.setNome(dadosAtualizados.getNome());
        categoriaExistente.setTipo(dadosAtualizados.getTipo());

        return categoriaRepository.save(categoriaExistente);
    }

    public void deletar(Long id) {
        Categoria categoria = buscarPorId(id);
        boolean temTransacoes = !transacaoRepository.findByCategoriaId(id).isEmpty();

        if (temTransacoes) {
            throw new RuntimeException("Não é possível excluir uma categoria que possui lançamentos. Exclua os lançamentos primeiro.");
        }

        categoriaRepository.delete(categoria);
    }
}
