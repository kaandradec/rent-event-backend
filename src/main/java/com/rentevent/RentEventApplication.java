package com.rentevent;

import com.rentevent.model.camion.Camion;
import com.rentevent.model.proveedor.Proveedor;
import com.rentevent.model.transporte.Transporte;
import com.rentevent.repository.ICamionRepository;
import com.rentevent.repository.IProveedorRepository;
import com.rentevent.repository.ITransporteRepository;
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

    public static void main(String[] args) {
        SpringApplication.run(RentEventApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Proveedor> proveedorList = iProveedorRepository.findAll();

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
        Proveedor proveedor = this.iProveedorRepository.findAll().get(1);

        List<Camion> camionList = iCamionRepository.findAll();
        List<Transporte> transporteList = iTransporteRepository.findAll();

        if (camionList.size() == 0) {
            iCamionRepository.saveAll(List.of(
                    Camion.builder()
                            .capacidad(25.8)
                            .marca("Volvo")
                            .matricula("ABC-1234")
                            .modelo("FH16")
                            .proveedor(proveedor)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(30.0)
                            .marca("Mercedes-Benz")
                            .matricula("XYZ-5678")
                            .modelo("Actros")
                            .proveedor(proveedor)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(20.5)
                            .marca("Scania")
                            .matricula("LMN-3456")
                            .modelo("R450")
                            .proveedor(proveedor)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(22.3)
                            .marca("MAN")
                            .matricula("DEF-7890")
                            .modelo("TGX")
                            .proveedor(proveedor)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(28.7)
                            .marca("DAF")
                            .matricula("GHI-1122")
                            .modelo("XF")
                            .proveedor(proveedor)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(27.4)
                            .marca("Iveco")
                            .matricula("JKL-3344")
                            .modelo("Stralis")
                            .proveedor(proveedor)
                            .tipo("Camión de carga media")
                            .build(),
                    Camion.builder()
                            .capacidad(24.2)
                            .marca("Renault")
                            .matricula("MNO-5566")
                            .modelo("T")
                            .proveedor(proveedor)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(29.5)
                            .marca("Volvo")
                            .matricula("PQR-7788")
                            .modelo("FH13")
                            .proveedor(proveedor)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(21.8)
                            .marca("Mercedes-Benz")
                            .matricula("STU-9900")
                            .modelo("Arocs")
                            .proveedor(proveedor)
                            .tipo("Camión de carga pesada")
                            .build(),
                    Camion.builder()
                            .capacidad(26.1)
                            .marca("Scania")
                            .matricula("VWX-1234")
                            .modelo("P320")
                            .proveedor(proveedor)
                            .tipo("Camión de carga pesada")
                            .build()
            ));


        }
        if (transporteList.size() == 0) {

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
