package org.bizbro.amocrm.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String token;
}
