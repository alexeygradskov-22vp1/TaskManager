package ru.psu.fvt.moipevm.taskmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.psu.fvt.moipevm.taskmanager.exceptions.DeleteException;
import ru.psu.fvt.moipevm.taskmanager.model.Card;
import ru.psu.fvt.moipevm.taskmanager.repositories.CardRepository;

import java.util.List;

@Service
public class CardServiceImpl implements BaseService<Card> {
    private final CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public String create(Card obj) {
        cardRepository.save(obj);
        return "";
    }

    @Override
    public List<Card> readAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card read(int id) {
        return cardRepository.getOne(id);
    }

    @Override
    public void update(Card obj) {
        cardRepository.save(obj);

    }

    @Override
    public void delete(int id) throws DeleteException {
        if (!cardRepository.existsById(id)) {
            throw new DeleteException("Card don't delete");
        }
        cardRepository.deleteById(id);
    }
}
