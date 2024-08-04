package com.example.intentcomextras_atv06

import java.io.Serializable

class Contato(var nome: String, var telefone: String, var email: String):Serializable {
    override fun toString(): String {
        return "Contato(nome='$nome', telefone='$telefone', email='$email')"
    }
}
