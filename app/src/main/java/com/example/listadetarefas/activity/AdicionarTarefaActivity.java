package com.example.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.listadetarefas.R;
import com.example.listadetarefas.helper.TarefaDAO;
import com.example.listadetarefas.models.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private  Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.txtTarefa);

        //Recuperar tarefa, caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configurar tarefa na caixa de texto
        if(tarefaAtual != null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemSalvar :

                if(tarefaAtual != null){
                    //editar

                    String nomeTarefa = editTarefa.getText().toString();
                    TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                    if(!nomeTarefa.isEmpty()){
                        Tarefa tarefa =  new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());

                        //atualizar no banco de dados
                        if(tarefaDAO.atualizar(tarefa)){
                            finish();
                            Toast.makeText(this, "Tarefa Atualizada com sucesso", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, "Erro ao atualizar Tarefa", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
//                    Executa ação de salvar
                    TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                    String nomeTarefa = editTarefa.getText().toString();

                    if(!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);

                        if(tarefaDAO.salvar(tarefa)){
                            finish();
                            Toast.makeText(this, "Tarefa salva com sucesso", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, "Erro ao salvar Tarefa", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}