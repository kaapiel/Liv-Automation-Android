package br.com.aguido.livautomation.service.livautomation.activate.model;

/**
 * Created by vilmar.filho on 1/20/16.
 */
public class ActivateRequest {

    private String cpf;
    private String activationCode;

    public ActivateRequest(String cpf, String activationCode) {
        this.cpf = cpf;
        this.activationCode = activationCode;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
