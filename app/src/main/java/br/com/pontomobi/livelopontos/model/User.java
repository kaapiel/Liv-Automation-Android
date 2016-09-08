package br.com.pontomobi.livelopontos.model;

/**
 * Created by selemafonso on 22/04/16.
 */
public class User {
    private String cpf;
    private String password;

    public User(String cpf, String password) {
        this.cpf = cpf;
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
