package org.bizbro.amocrm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tokens")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AmoToken {
    @Id
    private String id;
    @Column(columnDefinition = "text")
    private String token;
}
