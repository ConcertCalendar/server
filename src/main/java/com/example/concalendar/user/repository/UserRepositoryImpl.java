package com.example.concalendar.user.repository;

import com.example.concalendar.user.entity.QUser;
import com.example.concalendar.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    QUser qUser = QUser.user;
    @Override
    public User existsByNickname(String nickname){
        return queryFactory
                .select(qUser)
                .from(qUser)
                .where(qUser.userNickname.eq(nickname))
                .fetchOne();

    }

    @Override
    public User existsByUserEmail(String userEmail){
        return queryFactory
                .select(qUser)
                .from(qUser)
                .where(qUser.userEmail.eq(userEmail))
                .fetchOne();

    }

}
