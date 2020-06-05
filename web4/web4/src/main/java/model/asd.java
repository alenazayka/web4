package model;

import javax.persistence.*;

@Entity
@Table(name = "asd")
public class asd {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    private String brand;

}
