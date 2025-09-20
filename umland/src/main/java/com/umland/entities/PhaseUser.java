package com.umland.entities;

import jakarta.persistence.*;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import com.umland.entities.enums.PhaseStatus;

@Entity
public class PhaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "phase_id")
    private Phase phase;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PhaseStatus status;

    private int reputation;
    private int coins;
    
    @Column(nullable = false)
    private boolean isCurrent;
    
    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private String userDiagram;

    // Getters e setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Phase getPhase() { return phase; }
    public void setPhase(Phase phase) { this.phase = phase; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public PhaseStatus getStatus() { return status; }
    public void setStatus(PhaseStatus status) { this.status = status; }

    public int getReputation() { return reputation; }
    public void setReputation(int reputation) { this.reputation = reputation; }

    public int getCoins() { return coins; }
    public void setCoins(int coins) { this.coins = coins; }
    
    public boolean isCurrent() { return isCurrent; }
    public void setCurrent(boolean isCurrent) { this.isCurrent = isCurrent; }
    
    public String getUserDiagram() { return userDiagram; }
    public void setUserDiagram(String userDiagram) { this.userDiagram = userDiagram; }
}

