package com.example.intentcomextras_atv06

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.intentcomextras_atv06.databinding.ActivityMain2Binding
import com.google.android.material.snackbar.Snackbar
import android.content.DialogInterface
import android.content.Intent


class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private var position = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val main1 = Intent(this, MainActivity::class.java)
        val main3 = Intent(this, MainActivity3::class.java)

        val editBtn = binding.editBtn
        val killBtn = binding.killBtn

        val nome = binding.nomeDisplay
        val telefone = binding.telefoneDisplay
        val email = binding.emailDisplay
        val hintText = binding.hintTextView

        val listContact = intent.getSerializableExtra("lista") as ArrayList<Contato>
        val listNome = mutableListOf<String>()

        for (contato in listContact) {
            listNome.add("Contato: ${contato.nome}")
        }

        val listView = binding.listContacts
        listView.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listNome
        )

        listView.setOnItemClickListener { _, _, position, _ ->
            this.position = position
            val selectedContact = listContact[position]

            enableButtons()

            nome.isVisible = true
            telefone.isVisible = true
            email.isVisible = true
            hintText.isVisible = true

            nome.setText(selectedContact.nome)
            telefone.setText(selectedContact.telefone)
            email.setText(selectedContact.email)

            nome.setOnLongClickListener {
                copyToClipboard(nome, selectedContact)
                return@setOnLongClickListener false
            }
            telefone.setOnLongClickListener {
                copyToClipboard(telefone, selectedContact)
                return@setOnLongClickListener false
            }
            email.setOnLongClickListener {
                copyToClipboard(email, selectedContact)
                return@setOnLongClickListener false
            }
        }

        editBtn.setOnClickListener {
            disableButtons()

            AlertDialog.Builder(this)
                .setTitle("Editar os dados?")
                .setMessage("Deseja realmente mudar as informações do contato?")
                .setPositiveButton(
                    "Sim"
                ) { dialog: DialogInterface,
                    _: Int ->
                    main3.putExtra("lista", listContact)
                    main3.putExtra("position", position)
                    startActivity(main3)
                    dialog.dismiss()
                    finish()
                }
                .setNegativeButton(
                    "Não"
                ) { dialog: DialogInterface,
                    _: Int ->
                    enableButtons()
                    dialog.dismiss()
                }
                .create()
                .show()
        }

        killBtn.setOnClickListener {
            disableButtons()

            AlertDialog.Builder(this)
                .setTitle("Excluir contato pra sempre?")
                .setPositiveButton(
                    "Sim"
                ) { dialog: DialogInterface,
                    _: Int ->
                    if (listNome.size <= 1) {
                        // Sai após deletar o último item da lista
                        AlertDialog.Builder(this)
                            .setTitle("Você não tem mais contatos")
                            .setMessage(":(")
                            .setNeutralButton(
                                "Voltar"
                            ) { _: DialogInterface,
                                _: Int ->
                                startActivity(main1)
                                finish()
                            }
                            .create()
                            .show()
                    }
                    listContact.removeAt(position)
                    listNome.removeAt(position)
                    listView.adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        listNome
                    )
                    ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        listNome
                    ).notifyDataSetChanged()

                    nome.text.clear()
                    telefone.text.clear()
                    email.text.clear()

                    nome.isEnabled = false
                    telefone.isEnabled = false
                    email.isEnabled = false

                    dialog.dismiss()
                }
                .setNegativeButton(
                    "NÃO!"
                ) { dialog: DialogInterface,
                    _: Int ->
                    enableButtons()
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private fun copyToClipboard(button: EditText, contato: Contato) {
        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        if (button.id == this.binding.nomeDisplay.id) {
            val clip = ClipData.newPlainText(
                "Copied Text",
                "${contato.nome}"
            )
            clipboard.setPrimaryClip(clip)

            Snackbar.make(
                findViewById(R.id.main),
                "${contato.nome} copiado!",
                Snackbar.LENGTH_SHORT
            )
                .setBackgroundTint(getColor(R.color.black))
                .setTextColor(getColor(R.color.white))
                .show()
        } else if (button.id == this.binding.telefoneDisplay.id) {
            val clip = ClipData.newPlainText(
                "Copied Text",
                "${contato.telefone}"
            )
            clipboard.setPrimaryClip(clip)

            Snackbar.make(
                findViewById(R.id.main),
                "${contato.telefone} copiado!",
                Snackbar.LENGTH_SHORT
            )
                .setBackgroundTint(getColor(R.color.black))
                .setTextColor(getColor(R.color.white))
                .show()
        } else if (button.id == this.binding.emailDisplay.id) {
            val clip = ClipData.newPlainText(
                "Copied Text",
                "${contato.email}"
            )
            clipboard.setPrimaryClip(clip)

            Snackbar.make(
                findViewById(R.id.main),
                "${contato.email} copiado!",
                Snackbar.LENGTH_SHORT
            )
                .setBackgroundTint(getColor(R.color.black))
                .setTextColor(getColor(R.color.white))
                .show()
        }
    }

    private fun enableButtons() {
        binding.editBtn.isEnabled = true
        binding.editBtn.setTextColor(getColor(R.color.white))
        binding.editBtn.setBackgroundColor(getColor(R.color.black))
        binding.killBtn.isEnabled = true
        binding.killBtn.setTextColor(getColor(R.color.white))
        binding.killBtn.setBackgroundColor(getColor(R.color.black))
    }

    private fun disableButtons() {
        binding.editBtn.isEnabled = false
        binding.editBtn.setTextColor(getColor(R.color.t202020))
        binding.editBtn.setBackgroundColor(getColor(R.color.black))
        binding.killBtn.isEnabled = false
        binding.killBtn.setTextColor(getColor(R.color.t202020))
        binding.killBtn.setBackgroundColor(getColor(R.color.black))
    }
}