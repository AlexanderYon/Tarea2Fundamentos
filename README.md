# Lenguaje de Programación - Fundamentos de Ciencias de la Computación
Para desarrollar el proyecto se utilizó la version de java openjdk 23 2024-09-17.

## Requisitos
- Java: ejecute el comando `java --version` para comprobar si ya lo tiene instalado
- sableCC: chequear la [Página oficial de SableCC](https://sablecc.org/downloads)

## Instalación
Clone este repositorio:
```bash
git clone https://github.com/AlexanderYon/Tarea2Fundamentos.git
```
## Uso
Compile y ejecute la clase `Main.java` ubicada en la raíz del proyecto utilizando los comandos `javac` y `java`. La clase `Main.java` ejecuta el código
contenido en el archivo `Test.txt`, ubicado en el mismo directorio; puede modificar su contenido a gusto para realizar pruebas.

## Estructura del proyecto
En el directorio raíz se podrán encontrar los siguientes directorio / ficheros:

### exceptions/
Directorio que guarda excepciones personalizadas

### tarea/
Directorio que contiene todas las clases generadas por sableCC

### Ficheros
- `tarea.grammar`: Definición de la gramática
- `README.md`
- `Main.java`: Punto de partida del programa
- `Test.txt`: Código fuente a interpretar / ejecutar
- `Interpreter.java`: el programa principal
- `ArithmeticInterpreter.java`: usado por Interpreter.java y BooleanInterpreter.java para analizar expresiones aritméticas
- `BooleanInterpreter.java`: usado por Interpreter.java para analizar expresiones booleanas

El hecho de que estas clases hayan sido declaras fuera del directorio `tarea/` implica que se puede volver a ejecutar el comando `sablecc`
sin que estas clases se pierdan en el proceso.