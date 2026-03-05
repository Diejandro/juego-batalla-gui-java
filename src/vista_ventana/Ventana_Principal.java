package vista_ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

// TODO: Auto-generated Javadoc
/**
 * The Class Ventana_Principal. Aquí se agurparán todos los elementos visibles y
 * se dará funcionalidad a la batalla.
 */
public class Ventana_Principal {

	/** The lista heroes. */
	Lista_Batalla listaHeroes = new Lista_Batalla();

	/** The lista bestias. */
	Lista_Batalla listaBestias = new Lista_Batalla();

	/** The desarrollo combate. */
	JTextArea desarrolloCombate = new JTextArea();

	/** The heroes. */
	Creador_Personajes heroes = new Creador_Personajes();

	/** The bestias. */
	Creador_Personajes bestias = new Creador_Personajes();

	/** The etiqueta heroe. */
	JLabel etiquetaHeroe = new JLabel("Heroes: ");

	/** The etiqueta bestia. */
	JLabel etiquetaBestia = new JLabel("Bestias: ");

	/** The opciones heroe. */
	String[] opcionesHeroe = new String[] { "Humano", "Elfo", "Hobbit" };

	/** The opciones bestia. */
	String[] opcionesBestia = new String[] { "Orco", "Trasgo" };

	/**
	 * Instantiates a new ventana principal. Se dará forma a la ventana, se situará
	 * justo en el centro de la pantalla
	 */
	public Ventana_Principal() {
		// Se crea el Frame
		JFrame ventanaPpal = new JFrame("Batalla por la Tierra Media");
		ventanaPpal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Añado los paneles al Frame (Usando los métodos que corregimos antes)
		ventanaPpal.add(agregarGrupoNorte(), BorderLayout.NORTH);
		ventanaPpal.add(agregaGrupoSur(), BorderLayout.CENTER);

		// 1. FUERA EL PACK. Volvemos al tamaño manual, pero más ancho (800x700)
		ventanaPpal.setSize(800, 700);
		
		// 2. Te recomiendo dejarlo en 'true' mientras pruebas, así si algo no cuadra puedes estirar la ventana
		ventanaPpal.setResizable(true); 

		// 3. Esta línea mágica centra la ventana en la pantalla automáticamente
		ventanaPpal.setLocationRelativeTo(null);

		ventanaPpal.setVisible(true);
	}

	/**
	 * Agregar grupo norte. se añade en la ventana los dos formularios, situados uno
	 * a cada lado para crear el grupo de heroes y bestias.
	 *
	 * @return the j panel
	 */
	private JPanel agregarGrupoNorte() {
		// Solución: Usar GridLayout (1 fila, 2 columnas) para que se repartan el espacio
		JPanel grupoNorte = new JPanel(new GridLayout(1, 2));

		grupoNorte.add(heroes.agregarCrearPersonaje(etiquetaHeroe, opcionesHeroe, listaHeroes));
		grupoNorte.add(bestias.agregarCrearPersonaje(etiquetaBestia, opcionesBestia, listaBestias));

		return grupoNorte;
	}

	/**
	 * Agrega grupo sur. se añadirán en la parte sus los dos listados
	 * coresopndientes con el botón eliminar. Además, se añare un botón ue ejecutará
	 * el combate y un text area que imprimiá el resultado de la batalla.
	 *
	 * @return the j panel
	 */
	private JPanel agregaGrupoSur() {
		JPanel grupo = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// Solución clave: Obligar a los elementos a expandirse
		gbc.fill = GridBagConstraints.BOTH; 
		gbc.weightx = 1.0; // Darles peso para que ocupen lo ancho
		gbc.weighty = 0.5; // Darles peso para que ocupen lo alto

		gbc.gridx = 0; // columna 0
		gbc.gridy = 0; // fila 0
		gbc.insets = new Insets(20, 10, 20, 10);
		grupo.add(listaHeroes.agregarInfLista(), gbc);

		gbc.gridx = 1;
		grupo.add(listaBestias.agregarInfLista(), gbc);

		/* Botón de Lucha */
		JButton lucha = new JButton("A luchar!");
		lucha.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				lucha.setEnabled(false);
				
				// 1. Limpiamos la pantalla del combate anterior
				desarrolloCombate.setText(""); 
				
				// 2. Recreamos combatientes frescos desde cero
				listaHeroes.resetearEjercitos();
				listaBestias.resetearEjercitos();

				Thread hiloBatalla = new Thread(new Runnable() {
					@Override
					public void run() {
						Guerra.batalla(listaHeroes.listaHeroes(), listaBestias.listaBestias(), desarrolloCombate);
						
						// Volvemos a activar el botón de forma segura
						javax.swing.SwingUtilities.invokeLater(() -> lucha.setEnabled(true));
					}
				});
				hiloBatalla.start();
			}
		});
		
		gbc.gridx = 0; 
		gbc.gridy = 1; 
		gbc.gridwidth = 2; // Ocupa 2 columnas
		gbc.weighty = 0.1; // Menos peso para el botón, no necesita ser muy alto
		gbc.fill = GridBagConstraints.NONE; // El botón no queremos que se estire a lo bestia
		grupo.add(lucha, gbc);

		/* Text Area Combate */
		JPanel panelCombate = new JPanel(new BorderLayout()); // BorderLayout ayuda a expandir
		JScrollPane scrollPane = new JScrollPane(desarrolloCombate);
		scrollPane.setPreferredSize(new Dimension(600, 500));
		
		panelCombate.add(scrollPane, BorderLayout.CENTER);
		
		gbc.gridx = 0; 
		gbc.gridy = 2; 
		gbc.gridwidth = 2; 
		gbc.weighty = 3.0; // Mucho peso al texto para que ocupe todo lo de abajo
		gbc.fill = GridBagConstraints.BOTH; // Que se expanda
		grupo.add(panelCombate, gbc);

		EmptyBorder margen = new EmptyBorder(20, 20, 20, 20);
		LineBorder linea = new LineBorder(Color.BLACK);
		CompoundBorder bordCompuesto = new CompoundBorder(margen, linea);
		grupo.setBorder(bordCompuesto);
		
		return grupo;
	}
}

/*
 * JTextArea para poder mostrar el contenido debe recibir un String pero el
 * método guerra no devolvía nada en el proyecto anterior. La clase
 * TextAreaOutputStream permite redirigir la salida de un OutputStream a un
 * componente JTextArea, para mostrar mensajes de una interfaz gráfica de usuario.
 */
class TextAreaOutputStream extends OutputStream {

	private final JTextArea textArea;

	public TextAreaOutputStream(JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void write(int b) {
		textArea.append(String.valueOf((char) b));
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	@Override
	public void write(byte[] b, int off, int len) {
		textArea.append(new String(b, off, len));
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

}