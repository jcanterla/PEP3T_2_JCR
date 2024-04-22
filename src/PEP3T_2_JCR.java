import java.io.*;
import java.util.Scanner;

public class PEP3T_2_JCR {
    static RandomAccessFile fichero = null;

    public static void altaFactura() {
        Scanner teclado = new Scanner(System.in);
        try {
            fichero = new RandomAccessFile("src/facturas_telf.dat", "rw");
            System.out.println("\n\033[1;4mAlta de factura\033[0m");
            fichero.seek(fichero.length());
            System.out.print("Número de abonado: ");
            int numAbonado = teclado.nextInt();
            teclado.nextLine();

            fichero.seek(0);
            while (fichero.getFilePointer() < fichero.length()){
                // El bucle funcione mientras no llegue al final del fichero, devolvienvo la posicion del
                // puntero que sea menor que la longitud del fichero
                int num = fichero.readInt();
                String nombreFichero = fichero.readUTF();
                float valorFichero = fichero.readFloat();

                if (num == numAbonado){
                    System.out.println("\tAbonado ya registrado\n");
                    return;
                }
            }

            System.out.print("Nombre: ");
            String nombre = teclado.nextLine();
            System.out.print("Valor de la factura: ");
            String valorStr = teclado.next().replace(",", ".");
            float valor = Float.parseFloat(valorStr);
            teclado.nextLine();

            fichero.writeInt(numAbonado);
            fichero.writeUTF(nombre);
            fichero.writeFloat(valor);
            System.out.println("\tDatos incorporados al fichero\n");
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }

    private static void modificacionDelValorDeFactura() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("\n\033[1;4mModificación del valor de factura\033[0m");
        System.out.print("Número del abonado: ");
        int numAbonado = teclado.nextInt();
        boolean encontrado = false;
        try{
            fichero = new RandomAccessFile("src/facturas_telf.dat", "rw");
            while (fichero.getFilePointer() < fichero.length()){
                // El bucle funcione mientras no llegue al final del fichero, devolvienvo la posicion del
                // puntero que sea menor que la longitud del fichero
                int num = fichero.readInt();
                String nombre = fichero.readUTF();
                // Almacenamos posicion para luego poder modificar el valor
                long posicion = fichero.getFilePointer();
                float valor = fichero.readFloat();

                if (num == numAbonado){
                    System.out.println("\tValor de la factura: " + String.format("%.2f€", valor));
                    System.out.print("Nuevo valor de la factura: ");
                    String valorStr = teclado.next().replace(",", ".");
                    float nuevoValor = Float.parseFloat(valorStr);
                    fichero.seek(posicion);
                    fichero.writeFloat(nuevoValor);
                    encontrado = true;
                    System.out.println("\tDato modificado en el fichero\n");
                    break;
                }
            }
        }catch (FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }

        if(!encontrado){
            System.out.println("Abonado no registrado\n");
        }
    }

    private static void consultaFacturacionAbonado() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("\n\033[1;4mConsulta facturación abonado\033[0m");
        System.out.print("Número del abonado: ");
        int numAbonado = teclado.nextInt();
        boolean encontrado = false;
        try{
            fichero = new RandomAccessFile("src/facturas_telf.dat", "r");
            while (fichero.getFilePointer() < fichero.length()){
                // El bucle funcione mientras no llegue al final del fichero, devolvienvo la posicion del
                // puntero que sea menor que la longitud del fichero
                int num = fichero.readInt();
                String nombre = fichero.readUTF();
                float valor = fichero.readFloat();

                if (num == numAbonado){
                    System.out.println("\tValor de la factura: " + String.format("%.2f€", valor) + "\n");
                    encontrado = true;
                    break;
                }
            }
        }catch (FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }

        if(!encontrado){
            System.out.println("\tAbonado no registrado\n");
        }

    }

    private static void consultaFacturacionTotal() {
        System.out.println("\n\033[1;4mConsulta facturación total\033[0m");
        try{
            fichero = new RandomAccessFile("src/facturas_telf.dat", "r");
            float total = 0;
            while (fichero.getFilePointer() < fichero.length()){
                // El bucle funcione mientras no llegue al final del fichero, devolvienvo la posicion del
                // puntero que sea menor que la longitud del fichero
                int num = fichero.readInt();
                String nombre = fichero.readUTF();
                float valor = fichero.readFloat();
                total += valor;
            }
            System.out.println("\tFacturación total: " + String.format("%.2f€", total) + "\n");
        }catch (FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }

    private static void eliminarFichero() {
        System.out.println("\n\033[1;4mEliminar fichero\033[0m");
        File fichero = new File("src/facturas_telf.dat");
        if (fichero.delete()){
            System.out.println("\tFichero eliminado\n");
        }else{
            System.out.println("\tNo se ha podido eliminar el fichero\n");
        }
    }

/*
    private static void mostrarFichero() {
        try {
            fichero = new RandomAccessFile("src/facturas_telf.dat", "r");
            fichero.seek(0);
            while(fichero.getFilePointer() < fichero.length()){
                System.out.println("Número de abonado: " + fichero.readInt());
                System.out.println("Nombre: " + fichero.readUTF());
                System.out.println("Valor de la factura: " + fichero.readFloat());
            }
        } catch (EOFException eofe) {
            System.out.println("Fin de fichero");
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
*/

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;
        System.out.println("\t\t\t\t\t\t\033[1;4mPROGRAMA GESTIÓN COMPAÑÍA TELEFÓNICA\033[0m\n");
        do{
            System.out.println("\t\t\t\t\t\t\t\t\033[1mMenú de Opciones\033[0m");
            System.out.println("\t\t\t\t\t\t\t\t\033[1m================\033[0m\n");
            System.out.println("\t\t\t\t\t1) Alta de nuevas facturas");
            System.out.println("\t\t\t\t\t2) Modificación del valor de factura");
            System.out.println("\t\t\t\t\t3) Consulta del dato de facturación de un abonado");
            System.out.println("\t\t\t\t\t4) Consulta del dato de facturación total de la compañía");
            System.out.println("\t\t\t\t\t5) Eliminar el fichero");
            System.out.println("\t\t\t\t\t6) Salir");
            System.out.print("\t\t\t\t\t\t\t\t\t\033[1mOpción: \033[0m");

            try {
                opcion = teclado.nextInt();
            }catch (Exception e){
                System.out.println("\t\tIntroduzca un numero entero");
                teclado.nextLine();
                continue;

            }

            switch (opcion) {
                case 1:
                    altaFactura();
                    break;
                case 2: ;
                    modificacionDelValorDeFactura();
                    break;
                case 3:
                    consultaFacturacionAbonado();
                    break;
                case 4:
                    consultaFacturacionTotal();
                    break;
                case 5:
                    eliminarFichero();
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elige una opción del menú.");
                    break;
            }
        }while (opcion != 6);
    }
}


