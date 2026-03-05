package vista_ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
		ventanaPpal.setSize(800, 800);
		
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

		// --- 1. SECCIÓN DE LAS LISTAS ---
		gbc.fill = GridBagConstraints.BOTH; 
		gbc.weightx = 1.0; 
		gbc.weighty = 1.0; // Le devolvemos un peso de 1.0 para que no se dejen aplastar

		gbc.gridx = 0; 
		gbc.gridy = 0; 
		gbc.insets = new Insets(20, 10, 20, 10);
		grupo.add(listaHeroes.agregarInfLista(), gbc);

		gbc.gridx = 1;
		grupo.add(listaBestias.agregarInfLista(), gbc);

		// --- 2. SECCIÓN DEL BOTÓN ---
		JButton lucha = new JButton("A luchar!");
		lucha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lucha.setEnabled(false);
				desarrolloCombate.setText(""); 
				listaHeroes.resetearEjercitos();
				listaBestias.resetearEjercitos();

				Thread hiloBatalla = new Thread(new Runnable() {
					@Override
					public void run() {
						Guerra.batalla(listaHeroes.listaHeroes(), listaBestias.listaBestias(), desarrolloCombate);
						javax.swing.SwingUtilities.invokeLater(() -> lucha.setEnabled(true));
					}
				});
				hiloBatalla.start();
			}
		});
		
		gbc.gridx = 0; 
		gbc.gridy = 1; 
		gbc.gridwidth = 2; 
		gbc.weighty = 0.0; // ¡Clave! 0.0 significa que el botón ocupa solo lo justo y necesario
		gbc.fill = GridBagConstraints.NONE; 
		grupo.add(lucha, gbc);

		// --- 3. SECCIÓN DEL ÁREA DE TEXTO ---
		JPanel panelCombate = new JPanel(new BorderLayout()); 
		JScrollPane scrollPane = new JScrollPane(desarrolloCombate);
		
		// Le ponemos tu medida deseada como base
		scrollPane.setPreferredSize(new Dimension(600, 260));
		panelCombate.add(scrollPane, BorderLayout.CENTER);
		
		gbc.gridx = 0; 
		gbc.gridy = 2; 
		gbc.gridwidth = 2; 
		gbc.weighty = 1.5; // 1.5 es un 50% más alto que las listas (que tienen 1.0). Equilibrio perfecto.
		gbc.fill = GridBagConstraints.BOTH; 
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