package bo.edu.uajms.sistemasbjo.programacionmovil.jeremaus.a2026_tresenraya

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val rows = 3
    private val cols = 3


    private val tablero = Array(rows) { Array(cols) { "" } }

    private lateinit var botones: List<Button>
    private lateinit var txvEstado: TextView
    private var turnoX = true
    private var juegoTerminado = false

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_main)

        txvEstado = findViewById(R.id.TXVEstado)

        botones = listOf(
            findViewById(R.id.btnCasilla0), findViewById(R.id.btnCasilla1), findViewById(R.id.btnCasilla2),
            findViewById(R.id.btnCasilla3), findViewById(R.id.btnCasilla4), findViewById(R.id.btnCasilla5),
            findViewById(R.id.btnCasilla6), findViewById(R.id.btnCasilla7), findViewById(R.id.btnCasilla8)
        )


        for (i in botones.indices) {
            val row = i / cols
            val col = i % cols
            botones[i].setOnClickListener {
                click(row, col, botones[i])
            }
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            reiniciarJuego()
        }

        actualizarTurno()
    }


    private fun click(row: Int, col: Int, button: Button) {
        if (juegoTerminado || button.text != "") return

        val simbolo = if (turnoX) "X" else "O"
        button.text = simbolo
        tablero[row][col] = simbolo

        val hayGanador = verificarFilas() || verificarColumnas() || verificarDiagonales()

        if (hayGanador) {
            juegoTerminado = true
            deshabilitarBotones()
        } else if (verificarEmpate()) {
            juegoTerminado = true
            txvEstado.text = getString(R.string.empate)
            deshabilitarBotones()
        } else {
            turnoX = !turnoX
            actualizarTurno()
        }
    }

    private fun verificarEmpate(): Boolean {
        for (fila in 0 until rows) {
            for (columna in 0 until cols) {
                if (tablero[fila][columna] == "") return false
            }
        }
        return true
    }

    private fun deshabilitarBotones() {
        for (boton in botones) boton.isEnabled = false
    }


    private fun actualizarTurno() {
        txvEstado.text = if (turnoX) getString(R.string.turnoX) else getString(R.string.turnoO)
    }


    private fun reiniciarJuego() {
        for (boton in botones) {
            boton.text = ""
            boton.isEnabled = true
        }
        for (fila in 0 until rows) {
            for (columna in 0 until cols) {
                tablero[fila][columna] = ""
            }
        }
        turnoX = true
        juegoTerminado = false
        actualizarTurno()
    }

    //
    private fun verificarFilas(): Boolean {
        for (fila in 0 until rows) {
            if (tablero[fila][0] != "" && tablero[fila][0] == tablero[fila][1] && tablero[fila][1] == tablero[fila][2]) {
                txvEstado.text = getString(R.string.gano, tablero[fila][0])
                return true
            }
        }
        return false
    }

    // a la didadaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    private fun verificarColumnas(): Boolean {
        for (columna in 0 until cols) {
            if (tablero[0][columna] != "" && tablero[0][columna] == tablero[1][columna] && tablero[1][columna] == tablero[2][columna]) {
                txvEstado.text = getString(R.string.gano, tablero[0][columna])
                return true
            }
        }
        return false
    }


    private fun verificarDiagonales(): Boolean {
        if (tablero[0][0] != "" && tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2]) {
            txvEstado.text = getString(R.string.gano, tablero[0][0])
            return true
        }
        if (tablero[0][2] != "" && tablero[0][2] == tablero[1][1] && tablero[1][1] == tablero[2][0]) {
            txvEstado.text = getString(R.string.gano, tablero[0][2])
            return true
        }
        return false
    }
}