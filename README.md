# ⚔️ Batalla por la Tierra Media: Versión GUI

Este proyecto es una evolución visual del motor de combate original desarrollado en Java. Hemos pasado de una ejecución automática en consola a una **interfaz gráfica interactiva** que permite gestionar ejércitos en tiempo real.

## 🎮 Gameplay en Acción
https://github.com/user-attachments/assets/31ed69d1-b136-4df7-a742-ab53108b80f0



---

## 🛠️ Guía de la Interfaz
He diseñado la interfaz utilizando **Swing** y **AWT**, centrándome en la usabilidad y el flujo lógico del combate.

| Elemento | Función | Detalle Técnico |
| :--- | :--- | :--- |
| **Panel de Creación** | Permite introducir Nombre, Vida y Armadura. | Valida la instancia de clase (Elfo, Humano, etc.). |
| **Botón "Añadir"** | Inserta al personaje en la lista del bando correspondiente. | Actualiza dinámicamente el `DefaultListModel`. |
| **Botón "Eliminar"** | Quita a un combatiente seleccionado antes de la batalla. | Gestiona la eliminación segura en la `List`. |
| **Botón "¡A luchar!"** | Inicia el motor de combate por turnos. | Ejecuta la lógica de la clase `Guerra`. |
| **Área de Texto** | Narra el log de la batalla (dados, daño y muertes). | Usa `JTextArea` con auto-scroll para el feedback. |

---

## 🧠 Lógica y Tecnologías
Lo que hace que este juego funcione "bajo el capó":

* **Programación Orientada a Objetos (POO):** Uso extensivo de herencia y polimorfismo para diferenciar las habilidades de Héroes y Bestias.
* **Gestión de Turnos:** Implementación de `Iterators` para recorrer las listas de combate y gestionar las bajas de forma segura sin errores de concurrencia.
* **Sistema de Dados:** Refactorizado mediante una arquitectura basada en eventos para calcular daños críticos y modificadores raciales.
* **Layout Adaptativo:** Uso de `GridBagLayout` para asegurar que los elementos se mantengan alineados independientemente del contenido.

---

## 🚀 Cómo ejecutarlo
1. Clona el repositorio o descarga la carpeta `src`.
2. Abre el proyecto en tu IDE favorito (Eclipse recomendado).
3. Ejecuta la clase **`Ventana_Principal.java`**.
