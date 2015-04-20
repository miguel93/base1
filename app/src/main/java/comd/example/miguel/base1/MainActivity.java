package comd.example.miguel.base1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

// en este paso delcaramos las variables que vamos a utulzar
public class MainActivity extends ActionBarActivity {
    EditText edit_idusuario, edit_user, edit_nombre,
            edit_apellidop, edit_apellidom, edit_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Obtener los datos de los campos
        edit_idusuario = (EditText) findViewById(R.id.edit_idusuario);
        edit_user = (EditText) findViewById(R.id. edit_user);
        edit_nombre = (EditText) findViewById(R.id. edit_nombre);
        edit_apellidop = (EditText) findViewById(R.id. edit_apellidop);
        edit_apellidom = (EditText) findViewById(R.id. edit_apellidom);
        edit_email = (EditText) findViewById(R.id. edit_email);


    }

    //aqui en este paso declaramos para dar de alta un metodo para donde los campos que declaramos se ban a guardar en la base de dato, insertamos los registros aqui
    public void alta (View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "usuarios", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String idusuario = edit_idusuario.getText().toString();
        String user = edit_user.getText().toString();
        String nombre = edit_nombre.getText().toString();
        String apellidop = edit_apellidop.getText().toString();
        String apellidom = edit_apellidom.getText().toString();
        String email = edit_email.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("id_usuario", idusuario);
        registro.put("user", user);
        registro.put("nombre", nombre);
        registro.put("apellido_p", apellidop);
        registro.put("apellido_m", apellidom);
        registro.put("email", email);
// aqui indicamos que la base de datos se va a insertar los registros  que el usuario desee
        bd.insert("usuarios", null, registro);
        bd.close();

        edit_idusuario.setText("");
        edit_user.setText("");
        edit_nombre.setText("");
        edit_apellidop.setText("");
        edit_apellidom.setText("");
        edit_email.setText("");

        Toast.makeText(this, "el usuario se agrego correctamente", Toast.LENGTH_SHORT).show();
    }
   // aqui en este paso estamos creando una consilta la cual va entrar ala base de datos con las variables que se le esta indicando
    public void consulta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "usuarios", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String idusuario = edit_idusuario.getText().toString();
        Cursor fila = bd.rawQuery("select user, nombre, apellido_p, apellido_m, email from usuarios where id_usuario=" + idusuario, null);
        if (fila.moveToFirst()) {
            edit_user.setText(fila.getString(0));
            edit_nombre.setText(fila.getString(1));
            edit_apellidop.setText(fila.getString(2));
            edit_apellidom.setText(fila.getString(3));
            edit_email.setText(fila.getString(4));
        } else {
            Toast.makeText(this,"el usuario no existe",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void baja(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "usuarios", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String idusuario = edit_idusuario.getText().toString();
        int cant = bd.delete("usuarios","id_usuario=" + idusuario, null);
        bd.close();

        edit_idusuario.setText("");
        edit_user.setText("");
        edit_nombre.setText("");
        edit_apellidop.setText("");
        edit_apellidom.setText("");
        edit_email.setText("");

        if (cant == 1) {
            Toast.makeText(this, "usuario borrado correctamente",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "el usuario no existe",Toast.LENGTH_SHORT).show();
        }
    }

    public void modificacion (View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "usuarios", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String idusuario = edit_idusuario.getText().toString();
        String user = edit_user.getText().toString();
        String nombre = edit_nombre.getText().toString();
        String apellidop = edit_apellidop.getText().toString();
        String apellidom = edit_apellidom.getText().toString();
        String email = edit_email.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("id_usuario", idusuario);
        registro.put("user", user);
        registro.put("nombre", nombre);
        registro.put("apellido_p", apellidop);
        registro.put("apellido_m", apellidom);
        registro.put("email", email);

        int cant = bd.update("usuarios", registro, "id_usuario=" + idusuario, null);
        bd.close();

        if (cant == 1) {
            Toast.makeText(this, "Se modificaron los datos del usuario",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe el usuario",Toast.LENGTH_SHORT).show();
        }

    }
    public void limpia (View v){
        edit_idusuario.setText("");
        edit_user.setText("");
       // edit_password.setText("");
        edit_nombre.setText("");
        edit_apellidop.setText("");
        edit_apellidom.setText("");
        edit_email.setText("");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
