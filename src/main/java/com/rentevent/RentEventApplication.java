package com.rentevent;

import com.rentevent.model.camion.Camion;
import com.rentevent.model.patrocinador.Patrocinador;
import com.rentevent.model.proveedor.Proveedor;
import com.rentevent.model.transporte.Transporte;
import com.rentevent.repository.ICamionRepository;
import com.rentevent.repository.IPatrocinadorRepository;
import com.rentevent.repository.IProveedorRepository;
import com.rentevent.repository.ITransporteRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class RentEventApplication implements CommandLineRunner {

    @Autowired
    IProveedorRepository iProveedorRepository;
    @Autowired
    ICamionRepository iCamionRepository;
    @Autowired
    ITransporteRepository iTransporteRepository;
    @Autowired
    IPatrocinadorRepository iPatrocinadorRepository;

    public static void main(String[] args) {
        SpringApplication.run(RentEventApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Proveedor> proveedorList = iProveedorRepository.findAll();
        List<Patrocinador> patrocinadorList = iPatrocinadorRepository.findAll();
        List<Camion> camionList = iCamionRepository.findAll();
        List<Transporte> transporteList = iTransporteRepository.findAll();


        if (patrocinadorList.size() == 0) {
            iPatrocinadorRepository.save(
                    Patrocinador.builder()
                            .nombre("Rent-Event")
                            .descripcion("Despliegue publicitario de la marca")
                            .tipo("Servicios")
                            .build());
        }
        if (proveedorList.size() == 0) {
            iProveedorRepository.saveAll(List.of(Proveedor.builder()
                            .nombre("Renta Todo")
                            .build(),
                    Proveedor.builder()
                            .nombre("Lumiere")
                            .build(),
                    Proveedor.builder()
                            .nombre("Grupo Ambrosía")
                            .build()
            ));
        }
        if (camionList.size() == 0) {
            Proveedor proveedor1 = this.iProveedorRepository.findAll().get(1);
            iCamionRepository.saveAll(List.of(
                    Camion.builder()
                            .capacidad(25.8)
                            .marca("Volvo")
                            .matricula("ABC-1234")
                            .modelo("FH16")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(30.0)
                            .marca("Mercedes-Benz")
                            .matricula("XYZ-5678")
                            .modelo("Actros")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(20.5)
                            .marca("Scania")
                            .matricula("LMN-3456")
                            .modelo("R450")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(22.3)
                            .marca("MAN")
                            .matricula("DEF-7890")
                            .modelo("TGX")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(28.7)
                            .marca("DAF")
                            .matricula("GHI-1122")
                            .modelo("XF")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(27.4)
                            .marca("Iveco")
                            .matricula("JKL-3344")
                            .modelo("Stralis")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga media")
                            .build(),
                    Camion.builder()
                            .capacidad(24.2)
                            .marca("Renault")
                            .matricula("MNO-5566")
                            .modelo("T")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(29.5)
                            .marca("Volvo")
                            .matricula("PQR-7788")
                            .modelo("FH13")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(21.8)
                            .marca("Mercedes-Benz")
                            .matricula("STU-9900")
                            .modelo("Arocs")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(26.1)
                            .marca("Scania")
                            .matricula("VWX-1234")
                            .modelo("P320")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build()
            ));
            iCamionRepository.saveAll(List.of(
                    Camion.builder()
                            .capacidad(23.5)
                            .marca("Volvo")
                            .matricula("BCD-2345")
                            .modelo("FH12")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(31.0)
                            .marca("Mercedes-Benz")
                            .matricula("EFG-3456")
                            .modelo("Antos")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(22.0)
                            .marca("Scania")
                            .matricula("HIJ-4567")
                            .modelo("R500")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(24.8)
                            .marca("MAN")
                            .matricula("KLM-5678")
                            .modelo("TGS")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(29.0)
                            .marca("DAF")
                            .matricula("NOP-6789")
                            .modelo("CF")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(27.7)
                            .marca("Iveco")
                            .matricula("QRS-7890")
                            .modelo("Trakker")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga media")
                            .build(),
                    Camion.builder()
                            .capacidad(25.3)
                            .marca("Renault")
                            .matricula("TUV-8901")
                            .modelo("D")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(30.2)
                            .marca("Volvo")
                            .matricula("WXY-9012")
                            .modelo("FM")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(22.9)
                            .marca("Mercedes-Benz")
                            .matricula("YZA-0123")
                            .modelo("Axor")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(26.5)
                            .marca("Scania")
                            .matricula("BCD-1234")
                            .modelo("G440")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(24.6)
                            .marca("MAN")
                            .matricula("EFG-2345")
                            .modelo("TGM")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(28.3)
                            .marca("DAF")
                            .matricula("HIJ-3456")
                            .modelo("LF")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(29.9)
                            .marca("Iveco")
                            .matricula("KLM-4567")
                            .modelo("Eurocargo")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga media")
                            .build(),
                    Camion.builder()
                            .capacidad(25.0)
                            .marca("Renault")
                            .matricula("NOP-5678")
                            .modelo("C")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(32.1)
                            .marca("Volvo")
                            .matricula("QRS-6789")
                            .modelo("FMX")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(22.7)
                            .marca("Mercedes-Benz")
                            .matricula("TUV-7890")
                            .modelo("Atego")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(27.9)
                            .marca("Scania")
                            .matricula("WXY-8901")
                            .modelo("P410")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(23.1)
                            .marca("MAN")
                            .matricula("YZA-9012")
                            .modelo("TGE")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(28.4)
                            .marca("DAF")
                            .matricula("BCD-0123")
                            .modelo("XF105")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(29.7)
                            .marca("Iveco")
                            .matricula("EFG-1234")
                            .modelo("S-Way")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga media")
                            .build(),
                    Camion.builder()
                            .capacidad(25.9)
                            .marca("Renault")
                            .matricula("HIJ-2345")
                            .modelo("Kerax")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(31.5)
                            .marca("Volvo")
                            .matricula("KLM-3456")
                            .modelo("FH4")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(23.7)
                            .marca("Mercedes-Benz")
                            .matricula("NOP-4567")
                            .modelo("Econic")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(26.8)
                            .marca("Scania")
                            .matricula("QRS-5678")
                            .modelo("L360")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(24.4)
                            .marca("MAN")
                            .matricula("TUV-6789")
                            .modelo("TGL")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(27.2)
                            .marca("DAF")
                            .matricula("WXY-7890")
                            .modelo("CF85")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(28.1)
                            .marca("Iveco")
                            .matricula("YZA-8901")
                            .modelo("Daily")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga media")
                            .build(),
                    Camion.builder()
                            .capacidad(26.6)
                            .marca("Renault")
                            .matricula("BCD-9012")
                            .modelo("K")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(32.0)
                            .marca("Volvo")
                            .matricula("EFG-0123")
                            .modelo("FM11")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(24.1)
                            .marca("Mercedes-Benz")
                            .matricula("HIJ-1234")
                            .modelo("Unimog")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(27.3)
                            .marca("Scania")
                            .matricula("KLM-2345")
                            .modelo("G410")
                            .proveedor(proveedor1)
                            .tipo("Camión de carga pesada")
                            .build()
            ));


        }
        if (transporteList.size() == 0) {
            Proveedor proveedor = this.iProveedorRepository.findAll().get(0);
            iTransporteRepository.saveAll(List.of(
                    Transporte.builder()
                            .marca("Volkswagen")
                            .matricula("BCD-2345")
                            .modelo("Crafter")
                            .numPlazas("18")
                            .proveedor(proveedor)
                            .tipo("Buseta de transporte urbano")
                            .build(),
                    Transporte.builder()
                            .marca("Nissan")
                            .matricula("CDE-3456")
                            .modelo("Civilian")
                            .numPlazas("25")
                            .proveedor(proveedor)
                            .tipo("Buseta de empresa")
                            .build(),
                    Transporte.builder()
                            .marca("Mercedes-Benz")
                            .matricula("DEF-4567")
                            .modelo("Vario")
                            .numPlazas("20")
                            .proveedor(proveedor)
                            .tipo("Buseta escolar")
                            .build(),
                    Transporte.builder()
                            .marca("Hyundai")
                            .matricula("EFG-5678")
                            .modelo("H350")
                            .numPlazas("22")
                            .proveedor(proveedor)
                            .tipo("Buseta de turismo")
                            .build(),
                    Transporte.builder()
                            .marca("Ford")
                            .matricula("FGH-6789")
                            .modelo("Transit")
                            .numPlazas("19")
                            .proveedor(proveedor)
                            .tipo("Buseta interprovincial")
                            .build(),
                    Transporte.builder()
                            .marca("Renault")
                            .matricula("GHI-7890")
                            .modelo("Master")
                            .numPlazas("21")
                            .proveedor(proveedor)
                            .tipo("Buseta de transporte público")
                            .build(),
                    Transporte.builder()
                            .marca("Chevrolet")
                            .matricula("HIJ-8901")
                            .modelo("Express")
                            .numPlazas("23")
                            .proveedor(proveedor)
                            .tipo("Buseta escolar")
                            .build(),
                    Transporte.builder()
                            .marca("Isuzu")
                            .matricula("IJK-9012")
                            .modelo("Journey")
                            .numPlazas("24")
                            .proveedor(proveedor)
                            .tipo("Buseta de turismo")
                            .build(),
                    Transporte.builder()
                            .marca("Mitsubishi")
                            .matricula("JKL-0123")
                            .modelo("Rosa")
                            .numPlazas("28")
                            .proveedor(proveedor)
                            .tipo("Buseta interprovincial")
                            .build(),
                    Transporte.builder()
                            .marca("Toyota")
                            .matricula("KLM-1234")
                            .modelo("Hiace")
                            .numPlazas("17")
                            .proveedor(proveedor)
                            .tipo("Buseta de empresa")
                            .build(),
                    Transporte.builder()
                            .marca("Mercedes-Benz")
                            .matricula("LMN-2345")
                            .modelo("Sprinter")
                            .numPlazas("20")
                            .proveedor(proveedor)
                            .tipo("Buseta escolar")
                            .build(),
                    Transporte.builder()
                            .marca("Hyundai")
                            .matricula("MNO-3456")
                            .modelo("County")
                            .numPlazas("26")
                            .proveedor(proveedor)
                            .tipo("Buseta de turismo")
                            .build(),
                    Transporte.builder()
                            .marca("Ford")
                            .matricula("NOP-4567")
                            .modelo("Econoline")
                            .numPlazas("18")
                            .proveedor(proveedor)
                            .tipo("Buseta interprovincial")
                            .build(),
                    Transporte.builder()
                            .marca("Volkswagen")
                            .matricula("OPQ-5678")
                            .modelo("Transporter")
                            .numPlazas("19")
                            .proveedor(proveedor)
                            .tipo("Buseta de transporte urbano")
                            .build(),
                    Transporte.builder()
                            .marca("Nissan")
                            .matricula("PQR-6789")
                            .modelo("NV350")
                            .numPlazas("21")
                            .proveedor(proveedor)
                            .tipo("Buseta de empresa")
                            .build(),
                    Transporte.builder()
                            .marca("Chevrolet")
                            .matricula("QRS-7890")
                            .modelo("Van")
                            .numPlazas("24")
                            .proveedor(proveedor)
                            .tipo("Buseta escolar")
                            .build(),
                    Transporte.builder()
                            .marca("Renault")
                            .matricula("RST-8901")
                            .modelo("Traffic")
                            .numPlazas("22")
                            .proveedor(proveedor)
                            .tipo("Buseta de turismo")
                            .build(),
                    Transporte.builder()
                            .marca("Isuzu")
                            .matricula("STU-9012")
                            .modelo("Elf")
                            .numPlazas("25")
                            .proveedor(proveedor)
                            .tipo("Buseta interprovincial")
                            .build(),
                    Transporte.builder()
                            .marca("Mitsubishi")
                            .matricula("TUV-0123")
                            .modelo("Canter")
                            .numPlazas("28")
                            .proveedor(proveedor)
                            .tipo("Buseta de transporte público")
                            .build(),
                    Transporte.builder()
                            .marca("Toyota")
                            .matricula("UVW-1234")
                            .modelo("Coaster")
                            .numPlazas("30")
                            .proveedor(proveedor)
                            .tipo("Buseta escolar")
                            .build(),
                    Transporte.builder()
                            .marca("Hyundai")
                            .matricula("VWX-2345")
                            .modelo("H1")
                            .numPlazas("17")
                            .proveedor(proveedor)
                            .tipo("Buseta de empresa")
                            .build(),
                    Transporte.builder()
                            .marca("Ford")
                            .matricula("WXY-3456")
                            .modelo("Transit")
                            .numPlazas("19")
                            .proveedor(proveedor)
                            .tipo("Buseta escolar")
                            .build(),
                    Transporte.builder()
                            .marca("Nissan")
                            .matricula("XYZ-4567")
                            .modelo("Caravan")
                            .numPlazas("23")
                            .proveedor(proveedor)
                            .tipo("Buseta de turismo")
                            .build(),
                    Transporte.builder()
                            .marca("Mercedes-Benz")
                            .matricula("YZA-5678")
                            .modelo("Sprinter")
                            .numPlazas("22")
                            .proveedor(proveedor)
                            .tipo("Buseta interprovincial")
                            .build(),
                    Transporte.builder()
                            .marca("Volkswagen")
                            .matricula("ZAB-6789")
                            .modelo("Transporter")
                            .numPlazas("24")
                            .proveedor(proveedor)
                            .tipo("Buseta de transporte urbano")
                            .build(),
                    Transporte.builder()
                            .marca("Chevrolet")
                            .matricula("ABC-7890")
                            .modelo("Express")
                            .numPlazas("21")
                            .proveedor(proveedor)
                            .tipo("Buseta de empresa")
                            .build(),
                    Transporte.builder()
                            .marca("Renault")
                            .matricula("BCD-8901")
                            .modelo("Master")
                            .numPlazas("20")
                            .proveedor(proveedor)
                            .tipo("Buseta escolar")
                            .build(),
                    Transporte.builder()
                            .marca("Isuzu")
                            .matricula("CDE-9012")
                            .modelo("Journey")
                            .numPlazas("26")
                            .proveedor(proveedor)
                            .tipo("Buseta de turismo")
                            .build(),
                    Transporte.builder()
                            .marca("Mitsubishi")
                            .matricula("DEF-0123")
                            .modelo("Rosa")
                            .numPlazas("28")
                            .proveedor(proveedor)
                            .tipo("Buseta interprovincial")
                            .build(),
                    Transporte.builder()
                            .marca("Toyota")
                            .matricula("EFG-1234")
                            .modelo("Hiace")
                            .numPlazas("17")
                            .proveedor(proveedor)
                            .tipo("Buseta de transporte público")
                            .build()
            ));
            iTransporteRepository.saveAll(List.of(
                    Transporte.builder()
                            .marca("Toyota")
                            .matricula("ABC-1234")
                            .modelo("Coaster")
                            .numPlazas("30")
                            .proveedor(proveedor)
                            .tipo("Buseta escolar")
                            .build(),
                    Transporte.builder()
                            .marca("Mercedes-Benz")
                            .matricula("XYZ-5678")
                            .modelo("Sprinter")
                            .numPlazas("20")
                            .proveedor(proveedor)
                            .tipo("Buseta de turismo")
                            .build(),
                    Transporte.builder()
                            .marca("Hyundai")
                            .matricula("LMN-3456")
                            .modelo("County")
                            .numPlazas("25")
                            .proveedor(proveedor)
                            .tipo("Buseta interprovincial")
                            .build(),
                    Transporte.builder()
                            .marca("Volkswagen")
                            .matricula("DEF-7890")
                            .modelo("Crafter")
                            .numPlazas("20")
                            .proveedor(proveedor)
                            .tipo("Buseta de sporte urbano")
                            .build(),
                    Transporte.builder()
                            .marca("Nissan")
                            .matricula("GHI-1122")
                            .modelo("Civilian")
                            .numPlazas("26")
                            .proveedor(proveedor)
                            .tipo("Buseta de empresa")
                            .build(),
                    Transporte.builder()
                            .marca("Mitsubishi")
                            .matricula("JKL-3344")
                            .modelo("Rosa")
                            .numPlazas("28")
                            .proveedor(proveedor)
                            .tipo("Buseta escolar")
                            .build(),
                    Transporte.builder()
                            .marca("Ford")
                            .matricula("MNO-5566")
                            .modelo("Transit")
                            .numPlazas("18")
                            .proveedor(proveedor)
                            .tipo("Buseta de sporte público")
                            .build(),
                    Transporte.builder()
                            .marca("Chevrolet")
                            .matricula("PQR-7788")
                            .modelo("Express")
                            .numPlazas("22")
                            .proveedor(proveedor)
                            .tipo("Buseta de turismo")
                            .build(),
                    Transporte.builder()
                            .marca("Isuzu")
                            .matricula("STU-9900")
                            .modelo("Journey")
                            .numPlazas("24")
                            .proveedor(proveedor)
                            .tipo("Buseta interprovincial")
                            .build(),
                    Transporte.builder()
                            .marca("Renault")
                            .matricula("VWX-1234")
                            .modelo("Master")
                            .numPlazas("19")
                            .proveedor(proveedor)
                            .tipo("Buseta escolar")
                            .build()
            ));
        }


    }
}
