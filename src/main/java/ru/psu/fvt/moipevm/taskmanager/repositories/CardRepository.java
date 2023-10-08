package ru.psu.fvt.moipevm.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.psu.fvt.moipevm.taskmanager.model.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {
    @Query("""
                select count(Card) > 0 from Card
                join Board b on b.id = Board .idOfUser
                where b.idOfUser = :userId
            """)
    public boolean existsCardByUserId(
            @Param("userId") Integer id
    ); // return true/false; true - user card / false other user card and not access
}
