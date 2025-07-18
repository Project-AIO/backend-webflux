package com.idt.aiowebflux.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_client")
public class Client {
    @Id
    @Column(name = "clientId")
    private String clientId;
    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "position", length = 50)
    private String position;
}
