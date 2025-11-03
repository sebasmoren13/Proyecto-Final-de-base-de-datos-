package org.example;

import org.example.dao.*;
import org.example.model.*;
import org.example.util.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static ClienteDAO clienteDAO;
    private static VehiculoDAO vehiculoDAO;
    private static ServicioDAO servicioDAO;
    private static RegistroLavadoDAO registroLavadoDAO;

    public static void main(String[] args) {
        try (Connection connection = ConexionBD.obtenerConexion()) {
            // Inicializar DAOs
            clienteDAO = new ClienteDAOImpl(connection);
            vehiculoDAO = new VehiculoDAOImpl(connection);
            servicioDAO = new ServicioDAOImpl(connection);
            registroLavadoDAO = new RegistroLavadoDAOImpl(connection);

            Scanner scanner = new Scanner(System.in);
            int opcionPrincipal;

            do {
                System.out.println("\n=== MENÚ PRINCIPAL ===");
                System.out.println("1. Gestionar Clientes");
                System.out.println("2. Gestionar Vehículos");
                System.out.println("3. Gestionar Servicios");
                System.out.println("4. Gestionar Registros de Lavado");
                System.out.println("0. Salir");
                System.out.print("Selecciona una opción: ");
                opcionPrincipal = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer

                switch (opcionPrincipal) {
                    case 1:
                        menuClientes(scanner);
                        break;
                    case 2:
                        menuVehiculos(scanner);
                        break;
                    case 3:
                        menuServicios(scanner);
                        break;
                    case 4:
                        menuRegistrosLavado(scanner);
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida, intenta de nuevo.");
                }
            } while (opcionPrincipal != 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // -------------------------------
    // SUBMENÚ CLIENTES
    // -------------------------------
    private static void menuClientes(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE CLIENTES ---");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Consultar Cliente");
            System.out.println("3. Actualizar Cliente");
            System.out.println("4. Eliminar Cliente");
            System.out.println("5. Listar Clientes");
            System.out.println("0. Volver al menú principal");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarCliente(scanner);
                    break;
                case 2:
                    consultarCliente(scanner);
                    break;
                case 3:
                    actualizarCliente(scanner);
                    break;
                case 4:
                    eliminarCliente(scanner);
                    break;
                case 5:
                    listarClientes();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void registrarCliente(Scanner scanner) {
        System.out.print("ID del cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        Cliente cliente = new Cliente(id, nombre, apellido, telefono, email, direccion);
        clienteDAO.crear(cliente);
        System.out.println("Cliente registrado exitosamente.");
    }

    private static void consultarCliente(Scanner scanner) {
        System.out.print("ID del cliente a consultar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = clienteDAO.leer(id);
        if (cliente != null) {
            System.out.println("Cliente encontrado:");
            System.out.println(cliente.toStringLegible());
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void actualizarCliente(Scanner scanner) {
        System.out.print("ID del cliente a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = clienteDAO.leer(id);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println("Cliente actual: " + cliente.toStringLegible());
        System.out.println("Ingrese los nuevos datos (dejar en blanco para mantener actual):");

        System.out.print("Nombre [" + cliente.getNombre() + "]: ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) cliente.setNombre(nombre);

        System.out.print("Apellido [" + cliente.getApellido() + "]: ");
        String apellido = scanner.nextLine();
        if (!apellido.isEmpty()) cliente.setApellido(apellido);

        System.out.print("Teléfono [" + cliente.getTelefono() + "]: ");
        String telefono = scanner.nextLine();
        if (!telefono.isEmpty()) cliente.setTelefono(telefono);

        System.out.print("Email [" + cliente.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) cliente.setEmail(email);

        System.out.print("Dirección [" + cliente.getDireccion() + "]: ");
        String direccion = scanner.nextLine();
        if (!direccion.isEmpty()) cliente.setDireccion(direccion);

        clienteDAO.actualizar(cliente);
        System.out.println("Cliente actualizado exitosamente.");
    }

    private static void eliminarCliente(Scanner scanner) {
        System.out.print("ID del cliente a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = clienteDAO.leer(id);
        if (cliente != null) {
            System.out.println("¿Está seguro de eliminar al cliente: " + cliente.getNombre() + " " + cliente.getApellido() + "? (s/n)");
            String confirmacion = scanner.nextLine();
            if (confirmacion.equalsIgnoreCase("s")) {
                clienteDAO.eliminar(id);
                System.out.println("Cliente eliminado exitosamente.");
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void listarClientes() {
        List<Cliente> clientes = clienteDAO.listar();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("=== LISTA DE CLIENTES ===");
            for (Cliente cliente : clientes) {
                System.out.println(cliente.toStringLegible());
            }
        }
    }

    // -------------------------------
    // SUBMENÚ VEHÍCULOS
    // -------------------------------
    private static void menuVehiculos(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE VEHÍCULOS ---");
            System.out.println("1. Registrar Vehículo");
            System.out.println("2. Consultar Vehículo");
            System.out.println("3. Actualizar Vehículo");
            System.out.println("4. Eliminar Vehículo");
            System.out.println("5. Listar Vehículos");
            System.out.println("0. Volver al menú principal");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarVehiculo(scanner);
                    break;
                case 2:
                    consultarVehiculo(scanner);
                    break;
                case 3:
                    actualizarVehiculo(scanner);
                    break;
                case 4:
                    eliminarVehiculo(scanner);
                    break;
                case 5:
                    listarVehiculos();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void registrarVehiculo(Scanner scanner) {
        System.out.print("ID del vehículo: ");
        int vehiculoId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID del cliente dueño: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Marca: ");
        String marca = scanner.nextLine();

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();

        System.out.print("Placa: ");
        String placa = scanner.nextLine();

        System.out.print("Color: ");
        String color = scanner.nextLine();

        System.out.print("Tipo: ");
        String tipo = scanner.nextLine();

        Vehiculo vehiculo = new Vehiculo(vehiculoId, clienteId, marca, modelo, placa, color, tipo);
        vehiculoDAO.crear(vehiculo);
        System.out.println("Vehículo registrado exitosamente.");
    }

    private static void consultarVehiculo(Scanner scanner) {
        System.out.print("ID del vehículo a consultar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Vehiculo vehiculo = vehiculoDAO.leer(id);
        if (vehiculo != null) {
            System.out.println("Vehículo encontrado:");
            System.out.println(vehiculo.toString());
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }

    private static void actualizarVehiculo(Scanner scanner) {
        System.out.print("ID del vehículo a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Vehiculo vehiculo = vehiculoDAO.leer(id);
        if (vehiculo == null) {
            System.out.println("Vehículo no encontrado.");
            return;
        }

        System.out.println("Vehículo actual: " + vehiculo.toString());
        System.out.println("Ingrese los nuevos datos (dejar en blanco para mantener actual):");

        System.out.print("Marca [" + vehiculo.getMarca() + "]: ");
        String marca = scanner.nextLine();
        if (!marca.isEmpty()) vehiculo.setMarca(marca);

        System.out.print("Modelo [" + vehiculo.getModelo() + "]: ");
        String modelo = scanner.nextLine();
        if (!modelo.isEmpty()) vehiculo.setModelo(modelo);

        System.out.print("Placa [" + vehiculo.getPlaca() + "]: ");
        String placa = scanner.nextLine();
        if (!placa.isEmpty()) vehiculo.setPlaca(placa);

        System.out.print("Color [" + vehiculo.getColor() + "]: ");
        String color = scanner.nextLine();
        if (!color.isEmpty()) vehiculo.setColor(color);

        System.out.print("Tipo [" + vehiculo.getTipo() + "]: ");
        String tipo = scanner.nextLine();
        if (!tipo.isEmpty()) vehiculo.setTipo(tipo);

        vehiculoDAO.actualizar(vehiculo);
        System.out.println("Vehículo actualizado exitosamente.");
    }

    private static void eliminarVehiculo(Scanner scanner) {
        System.out.print("ID del vehículo a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Vehiculo vehiculo = vehiculoDAO.leer(id);
        if (vehiculo != null) {
            System.out.println("¿Está seguro de eliminar el vehículo: " + vehiculo.getMarca() + " " + vehiculo.getModelo() + "? (s/n)");
            String confirmacion = scanner.nextLine();
            if (confirmacion.equalsIgnoreCase("s")) {
                vehiculoDAO.eliminar(id);
                System.out.println("Vehículo eliminado exitosamente.");
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }

    private static void listarVehiculos() {
        List<Vehiculo> vehiculos = vehiculoDAO.listar();
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
        } else {
            System.out.println("=== LISTA DE VEHÍCULOS ===");
            for (Vehiculo vehiculo : vehiculos) {
                System.out.println(vehiculo.toString());
            }
        }
    }

    // -------------------------------
    // SUBMENÚ SERVICIOS
    // -------------------------------
    private static void menuServicios(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE SERVICIOS ---");
            System.out.println("1. Registrar Servicio");
            System.out.println("2. Consultar Servicio");
            System.out.println("3. Actualizar Servicio");
            System.out.println("4. Eliminar Servicio");
            System.out.println("5. Listar Servicios");
            System.out.println("0. Volver al menú principal");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarServicio(scanner);
                    break;
                case 2:
                    consultarServicio(scanner);
                    break;
                case 3:
                    actualizarServicio(scanner);
                    break;
                case 4:
                    eliminarServicio(scanner);
                    break;
                case 5:
                    listarServicios();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void registrarServicio(Scanner scanner) {
        System.out.print("ID del servicio: ");
        int servicioId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nombre del servicio: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        scanner.nextLine();

        Servicio servicio = new Servicio(servicioId, nombre, precio);
        servicioDAO.crear(servicio);
        System.out.println("Servicio registrado exitosamente.");
    }

    private static void consultarServicio(Scanner scanner) {
        System.out.print("ID del servicio a consultar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Servicio servicio = servicioDAO.leer(id);
        if (servicio != null) {
            System.out.println("Servicio encontrado:");
            System.out.println(servicio.toString());
        } else {
            System.out.println("Servicio no encontrado.");
        }
    }

    private static void actualizarServicio(Scanner scanner) {
        System.out.print("ID del servicio a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Servicio servicio = servicioDAO.leer(id);
        if (servicio == null) {
            System.out.println("Servicio no encontrado.");
            return;
        }

        System.out.println("Servicio actual: " + servicio.toString());
        System.out.println("Ingrese los nuevos datos (dejar en blanco para mantener actual):");

        System.out.print("Nombre [" + servicio.getNombre() + "]: ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) servicio.setNombre(nombre);

        System.out.print("Precio [" + servicio.getPrecio() + "]: ");
        String precioStr = scanner.nextLine();
        if (!precioStr.isEmpty()) {
            try {
                servicio.setPrecio(Double.parseDouble(precioStr));
            } catch (NumberFormatException e) {
                System.out.println("Precio inválido, se mantiene el actual.");
            }
        }

        servicioDAO.actualizar(servicio);
        System.out.println("Servicio actualizado exitosamente.");
    }

    private static void eliminarServicio(Scanner scanner) {
        System.out.print("ID del servicio a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Servicio servicio = servicioDAO.leer(id);
        if (servicio != null) {
            System.out.println("¿Está seguro de eliminar el servicio: " + servicio.getNombre() + "? (s/n)");
            String confirmacion = scanner.nextLine();
            if (confirmacion.equalsIgnoreCase("s")) {
                servicioDAO.eliminar(id);
                System.out.println("Servicio eliminado exitosamente.");
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } else {
            System.out.println("Servicio no encontrado.");
        }
    }

    private static void listarServicios() {
        List<Servicio> servicios = servicioDAO.listar();
        if (servicios.isEmpty()) {
            System.out.println("No hay servicios registrados.");
        } else {
            System.out.println("=== LISTA DE SERVICIOS ===");
            for (Servicio servicio : servicios) {
                System.out.println(servicio.toString());
            }
        }
    }

    // -------------------------------
    // SUBMENÚ REGISTROS DE LAVADO
    // -------------------------------
    private static void menuRegistrosLavado(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE REGISTROS DE LAVADO ---");
            System.out.println("1. Registrar Lavado");
            System.out.println("2. Consultar Lavado");
            System.out.println("3. Actualizar Lavado");
            System.out.println("4. Eliminar Lavado");
            System.out.println("5. Listar Lavados");
            System.out.println("0. Volver al menú principal");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarLavado(scanner);
                    break;
                case 2:
                    consultarLavado(scanner);
                    break;
                case 3:
                    actualizarLavado(scanner);
                    break;
                case 4:
                    eliminarLavado(scanner);
                    break;
                case 5:
                    listarLavados();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void registrarLavado(Scanner scanner) {
        System.out.print("ID del registro: ");
        int registroId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID del vehículo: ");
        int vehiculoId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID del servicio: ");
        int servicioId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Fecha de lavado (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();

        System.out.print("Hora de inicio (HH:MM:SS): ");
        String horaInicio = scanner.nextLine();

        System.out.print("Hora de fin (HH:MM:SS): ");
        String horaFin = scanner.nextLine();

        System.out.print("Precio total: ");
        double precioTotal = scanner.nextDouble();
        scanner.nextLine();

        RegistroLavado registro = new RegistroLavado(registroId, vehiculoId, servicioId, fecha, horaInicio, horaFin, precioTotal);
        registroLavadoDAO.crear(registro);
        System.out.println("Registro de lavado creado exitosamente.");
    }

    private static void consultarLavado(Scanner scanner) {
        System.out.print("ID del registro a consultar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        RegistroLavado registro = registroLavadoDAO.leer(id);
        if (registro != null) {
            System.out.println("Registro encontrado:");
            System.out.println(registro.toString());
        } else {
            System.out.println("Registro no encontrado.");
        }
    }

    private static void actualizarLavado(Scanner scanner) {
        System.out.print("ID del registro a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        RegistroLavado registro = registroLavadoDAO.leer(id);
        if (registro == null) {
            System.out.println("Registro no encontrado.");
            return;
        }

        System.out.println("Registro actual: " + registro.toString());
        System.out.println("Ingrese los nuevos datos (dejar en blanco para mantener actual):");

        System.out.print("Fecha [" + registro.getFechaLavado() + "]: ");
        String fecha = scanner.nextLine();
        if (!fecha.isEmpty()) registro.setFechaLavado(fecha);

        System.out.print("Hora inicio [" + registro.getHoraInicio() + "]: ");
        String horaInicio = scanner.nextLine();
        if (!horaInicio.isEmpty()) registro.setHoraInicio(horaInicio);

        System.out.print("Hora fin [" + registro.getHoraFin() + "]: ");
        String horaFin = scanner.nextLine();
        if (!horaFin.isEmpty()) registro.setHoraFin(horaFin);

        System.out.print("Precio total [" + registro.getPrecioTotal() + "]: ");
        String precioStr = scanner.nextLine();
        if (!precioStr.isEmpty()) {
            try {
                registro.setPrecioTotal(Double.parseDouble(precioStr));
            } catch (NumberFormatException e) {
                System.out.println("Precio inválido, se mantiene el actual.");
            }
        }

        registroLavadoDAO.actualizar(registro);
        System.out.println("Registro actualizado exitosamente.");
    }

    private static void eliminarLavado(Scanner scanner) {
        System.out.print("ID del registro a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        RegistroLavado registro = registroLavadoDAO.leer(id);
        if (registro != null) {
            System.out.println("¿Está seguro de eliminar el registro del " + registro.getFechaLavado() + "? (s/n)");
            String confirmacion = scanner.nextLine();
            if (confirmacion.equalsIgnoreCase("s")) {
                registroLavadoDAO.eliminar(id);
                System.out.println("Registro eliminado exitosamente.");
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } else {
            System.out.println("Registro no encontrado.");
        }
    }

    private static void listarLavados() {
        List<RegistroLavado> registros = registroLavadoDAO.listar();
        if (registros.isEmpty()) {
            System.out.println("No hay registros de lavado.");
        } else {
            System.out.println("=== LISTA DE REGISTROS DE LAVADO ===");
            for (RegistroLavado registro : registros) {
                System.out.println(registro.toString());
            }
        }
    }
}