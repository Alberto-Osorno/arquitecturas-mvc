# Arquitectura MVC

Repositorio para el ADA de la arquitectura Modelo-Vista-Controlador

# Situación

La empresa necesita una aplicación de consola para registrar y consultar pedidos. El sistema debe permitir:

- Registrar un pedido.
- Consultar un pedido por identificador.
- Listar pedidos.
- Mostrar errores de validación.

## Estructura requerida

La solución deberá contener como mínimo:

```text
Usuario
   ↓
Vista
   ↓ evento
Controlador
   ↓ operación
Modelo
```

El flujo esperado es:

```text
Usuario
   ↓
Vista
   ↓
Controlador
   ↓
Modelo
   ↓
Controlador
   ↓
Vista
   ↓
Usuario
```

### Modelo

El Modelo deberá contener:

- Información de pedidos.
- Reglas de validación.
- Cálculo de subtotal.
- Cálculo de descuento.
- Cálculo de impuestos.
- Cálculo del total.
- Registro y consulta de pedidos.

Una posible clase:

```java
public class PedidoModelo {

    private Map<Integer, Pedido> pedidos = new HashMap<>();
    private int siguienteId = 1;

    public Pedido registrarPedido(Pedido pedido) {
        // validar
        // calcular
        // almacenar
        // devolver resultado
        return pedido;
    }

    public Pedido consultarPedido(int id) {
        return pedidos.get(id);
    }
}
```

*El Modelo no deberá imprimir información ni solicitar datos al usuario.*

### Vista

La Vista será inicialmente de consola. Por ejemplo:

```java
public class PedidoVista {

    public Pedido capturarPedido() {
        // capturar o construir datos
        return pedido;
    }

    public void mostrarResultado(Pedido pedido) {
        // mostrar datos
    }

    public void mostrarError(String mensaje) {
        // mostrar error
    }

    public void mostrarPedido(Pedido pedido) {
        // mostrar consulta
    }
}
```

La Vista:
- Presenta información.
- Captura acciones.
- Muestra mensajes.

No deberá:
- Calcular descuentos.
- Calcular impuestos.
- Almacenar pedidos.
- Aplicar reglas de negocio.

### Controlador

El Controlador coordinará la interacción:

```java
public class PedidoControlador {

    private PedidoModelo modelo;
    private PedidoVista vista;

    public PedidoControlador(PedidoModelo modelo, PedidoVista vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void registrarPedido() {
        try {
            Pedido pedido = vista.capturarPedido();
            Pedido resultado = modelo.registrarPedido(pedido);
            vista.mostrarResultado(resultado);
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        }
    }
}
```

*El Controlador no deberá contener las reglas de cálculo del pedido.*

## Modelo de dominio mínimo

Puede mantenerse:

**Producto**
- nombre
- precio
- cantidad
- existencia

**Pedido**
- id
- cliente
- productos
- subtotal
- descuento
- impuestos
- total
- estado

## Reglas de negocio

Se usarán exactamente las mismas que en el sistema en capas para comparar arquitecturas y no cambiar de problema:

- El cliente no puede estar vacío.
- Debe existir al menos un producto.
- La cantidad debe ser mayor que cero.
- No puede superar la existencia.
- Subtotal: $subtotal = \sum (\text{precio} \times \text{cantidad})$
- Descuento del 10 % si $subtotal \geq \$1,000$.
- Impuesto del 16 % sobre $subtotal - descuento$.
- Estado final = `PROCESADO`.

## Primera parte: implementar MVC

El proyecto podría organizarse así:

```text
src
│
├── modelo
│   ├── Pedido.java
│   ├── Producto.java
│   └── PedidoModelo.java
│
├── vista
│   └── PedidoVista.java
│
├── controlador
│   └── PedidoControlador.java
│
└── Principal.java
```

En `Principal` se construye el MVC:

```java
PedidoModelo modelo = new PedidoModelo();
PedidoVista vista = new PedidoVista();

PedidoControlador controlador = new PedidoControlador(modelo, vista);

controlador.registrarPedido();
```

## Segunda parte: comprobar la separación

La empresa quiere una segunda forma de visualizar los pedidos.

```java
public class PedidoVistaResumida extends PedidoVista {
    ...
}
```

Esta Vista deberá mostrar únicamente:

```text
Pedido: 15
Total: $3915.00
Estado: PROCESADO
```

La condición será: **No se permite modificar `PedidoModelo`.**

Después deberán ejecutar el mismo Modelo con:
- `PedidoVista`
- `PedidoVistaResumida`

Esto hace observable la reutilización del Modelo con distintas representaciones.

## Tercera parte: agregar consulta

Deberán incorporar:

```java
public void consultarPedido(int id)
```

en el Controlador.

El flujo esperado será:

```text
Vista
  ↓ id
Controlador
  ↓
Modelo.consultarPedido(id)
  ↓
Controlador
  ↓
Vista.mostrarPedido()
```

Si el pedido no existe:

```java
vista.mostrarError("Pedido no encontrado");
```

## Evidencias

- Registrar pedido válido con descuento.
- Registrar pedido válido sin descuento.
- Intentar registrar un pedido inválido.
- Consultar un pedido existente.
- Consultar un pedido inexistente.
- Ejecutar el mismo Modelo con la Vista normal y la Vista resumida.

## Entregables

- Proyecto Java completo.
- Código fuente.
- Diagrama UML de la arquitectura implementada.
- Evidencias de ejecución de los casos.
- Entrega por equipo.

**Formato del archivo:** `Apellido1Apellido2Apellido3Apellido4.ZIP`

**Fecha límite de entrega:** 29 de septiembre.
