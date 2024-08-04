package com.example.intentcomextras_atv06

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.intentcomextras_atv06.databinding.ActivityMain3Binding
import java.io.Serializable

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val nome = binding.nome
        val telefone = binding.telefone
        val email = binding.email
        val editBtn = binding.editBtn

        val list = intent.getSerializableExtra("lista") as ArrayList<Contato>
        val pos = intent.getIntExtra("position", -1)

        val main1 = Intent(this, MainActivity::class.java)
        val main2 = Intent(this, MainActivity2::class.java)

        nome.setText(list[pos].nome)
        telefone.setText(list[pos].telefone)
        email.setText(list[pos].email)

        editBtn.setOnClickListener {
            if (
                nome.text.isNotBlank() &&
                telefone.text.isNotBlank() &&
                email.text.isNotBlank()
            ) {
                list[pos].nome = nome.text.toString()
                list[pos].telefone = telefone.text.toString()
                list[pos].email = email.text.toString()

                main1.putExtra("lista", list as Serializable)
                main2.putExtra("lista", list as Serializable)

                AlertDialog.Builder(this)
                    .setTitle("Pronto!")
                    .setMessage("Contato editado com sucesso!")
                    .setNeutralButton(
                        "Certo"
                    ) { dialog: DialogInterface,
                        _: Int ->
                        dialog.dismiss()
                        startActivity(main2)
                        finish()
                    }
                    .create()
                    .show()
            } else {
                if (nome.text.isNullOrBlank()) binding.nomeLayout.error = "Você esqueceu aqui!"
                else binding.nomeLayout.error = ""
                if (telefone.text.isNullOrBlank()) binding.telefoneLayout.error = "Você esqueceu aqui!"
                else binding.telefoneLayout.error = ""
                if (email.text.isNullOrBlank()) binding.emailLayout.error = "Você esqueceu aqui!"
                else binding.emailLayout.error = ""
            }
        }
    }
}