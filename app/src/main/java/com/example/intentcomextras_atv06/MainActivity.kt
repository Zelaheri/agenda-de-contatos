package com.example.intentcomextras_atv06

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.intentcomextras_atv06.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nome = binding.nome
        val telefone = binding.telefone
        val email = binding.email

        val addBtn = binding.addListBtn
        val listBtn = binding.viewListBtn

        val listContato = ArrayList<Contato>()

        val main2 = Intent(this, MainActivity2::class.java)

        addBtn.setOnClickListener {
            if (
                nome.text.isNotBlank() &&
                telefone.text.isNotBlank() &&
                email.text.isNotBlank()
            ) {
                listContato.add(
                    Contato(
                        nome.text.toString().trim(),
                        telefone.text.toString().trim(),
                        email.text.toString().trim()
                    )
                )
                clearFields()
                clearError()

                Snackbar.make(
                    findViewById(R.id.main),
                    "Contato adicionado!",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                if (nome.text.isNullOrBlank()) binding.nomeLayout.error = "Você esqueceu aqui!"
                else binding.nomeLayout.error = ""
                if (telefone.text.isNullOrBlank()) binding.telefoneLayout.error = "Você esqueceu aqui!"
                else binding.telefoneLayout.error = ""
                if (email.text.isNullOrBlank()) binding.emailLayout.error = "Você esqueceu aqui!"
                else binding.emailLayout.error = ""
            }
        }

        listBtn.setOnClickListener {
            if (listContato.isEmpty()){
                AlertDialog.Builder(this)
                    .setTitle("Calma")
                    .setMessage("A lista de contatos ainda está vazia!")
                    .setNeutralButton("Ok", null)
                    .create()
                    .show()
            } else {
                main2.putExtra("lista", listContato as Serializable)
                startActivity(main2)
                finish()
            }
        }
    }

    private fun clearFields() {
        binding.nome.text.clear()
        binding.telefone.text.clear()
        binding.email.text.clear()
    }

    private fun clearError() {
        binding.nomeLayout.error = ""
        binding.telefoneLayout.error = ""
        binding.emailLayout.error = ""
    }
}