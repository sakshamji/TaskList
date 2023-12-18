package com.projectLearn.TaskList.repository;

import com.projectLearn.TaskList.entites.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token,Integer> {
    @Query("""
            select t from Token t inner join User u on t.user.userId = u.userId
            where u.userId=:user_id
            """)
    Token findTokenByUser(String user_id);
    @Query(
            """
                    select t from Token t where t.token=:jwt
                    """
    )
    Token findByToken(String jwt);
}
