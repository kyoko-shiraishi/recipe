package com.example.demo.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Img {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String path;

    @Column(nullable =  false)
    private String name;
    
 

    // --- getter/setter ---
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

 
    @Override
    public String toString() {
        return this.path;  // 画像パスを返す
    }
}
