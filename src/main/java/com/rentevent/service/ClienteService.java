package com.rentevent.service;

import com.rentevent.dto.request.*;
import com.rentevent.dto.response.DatosFacturacionResponse;
import com.rentevent.dto.response.ListarTarjetaResponse;
import com.rentevent.dto.response.TarjetaResponse;
import com.rentevent.exception.NotFoundException;
import com.rentevent.model.cliente.Cliente;
import com.rentevent.model.datos_facturacion.DatosFacturacion;
import com.rentevent.model.tarjeta.Tarjeta;
import com.rentevent.repository.IClienteRepository;
import com.rentevent.repository.IDatosFacturacionRepository;
import com.rentevent.repository.ITarjetaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    @Autowired
    private final IClienteRepository clienteRepository;
    @Autowired
    private IDatosFacturacionRepository datosFacturacionRepository;
    @Autowired
    private ITarjetaRepository iTarjetaRepository;


    public Optional<Cliente> obtenerClientePorUsername(String username) {
        return clienteRepository.findByCorreo(username);
    }

    @Transactional
    public void actualizarClienteTelefono(ClienteTelefonoRequest clienteTelefonoRequest) {
        Cliente temp = this.clienteRepository.findByCorreo(clienteTelefonoRequest.getCorreo()).orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        temp.setTelefono(clienteTelefonoRequest.getTelefono());
        temp.setPrefijo(clienteTelefonoRequest.getPrefijo());
        clienteRepository.save(temp);
    }

    @Transactional
    public void actualizarClienteRegion(ClienteRegionRequest clienteRegionRequest) {
        Cliente temp = this.clienteRepository.findByCorreo(clienteRegionRequest.getCorreo()).orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        temp.setPais(clienteRegionRequest.getPais());
        temp.setRegion(clienteRegionRequest.getRegion());
        clienteRepository.save(temp);
    }

    @Transactional
    public void registrarTarjeta(TarjetaRequest request) {
        Cliente temp = this.clienteRepository.findByCorreo(request.getCorreo()).orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        // Crea una nueva tarjeta a partir del request
        Tarjeta nuevaTarjeta = Tarjeta.builder()
                .fechaExpiracion(Date.valueOf(LocalDate.of(Integer.valueOf(request.getAnio())
                        , Integer.valueOf(request.getMes()), 1)))
                .digitos(request.getNumero())
                .token(request.getNumero().substring(0, 3) + "*******" + request.getNumero().substring(request.getNumero().length() - 3))
                .nombre(request.getNombre())
                .tipo(request.getTipoTarjetaSeleccionada())
                .cliente(temp).build();

        // Si el cliente ya tiene una lista de tarjetas, la actualiza
        if (temp.getTarjetas() == null) {
            temp.setTarjetas(new ArrayList<>());
        }
        temp.getTarjetas().add(nuevaTarjeta);

        // Guarda los cambios en el repositorio
        clienteRepository.save(temp);
    }

    public DatosFacturacionResponse obtenerDatosFacturacion(String usuario) {
        Cliente cliente = clienteRepository.findByCorreo(usuario).orElseThrow();
        DatosFacturacion datosFacturacion = cliente.getDatosFacturacion();
        if (datosFacturacion == null) {
            return DatosFacturacionResponse.builder().cedula("").direccion("").nombre("").build();
        } else {
            return DatosFacturacionResponse.builder().cedula(datosFacturacion.getCedulaCliente()).direccion(datosFacturacion.getDireccionCliente()).nombre(datosFacturacion.getNombreCliente()).build();
        }
    }

    @Transactional
    public void actualizarDatosFacturacion(DatosFacturacionRequest request) throws Exception {
        Cliente cliente = clienteRepository.findByCorreo(request.getCorreo()).orElseThrow();

        DatosFacturacion datosFacturacion = cliente.getDatosFacturacion();
        if (datosFacturacion == null) {
            datosFacturacion = DatosFacturacion.builder().cedulaCliente(request.getCedula()).direccionCliente(request.getDireccion()).nombreCliente(request.getNombre()).build();
        } else {
            datosFacturacion.setCedulaCliente(request.getCedula());
            datosFacturacion.setDireccionCliente(request.getDireccion());
            datosFacturacion.setNombreCliente(request.getNombre());
        }

        // Save the DatosFacturacion entity
        datosFacturacion = datosFacturacionRepository.save(datosFacturacion);

        // Set the saved DatosFacturacion to the cliente entity
        cliente.setDatosFacturacion(datosFacturacion);

        // Save the cliente entity
        clienteRepository.save(cliente);
    }


    public ListarTarjetaResponse obtenerTarjeta(String usuario) {
        Cliente cliente = clienteRepository.findByCorreo(usuario).orElseThrow();
        List<Tarjeta> tarjetaList = cliente.getTarjetas();
        List<TarjetaResponse> tarjetaResponseList = new ArrayList<>();

        if (tarjetaList == null) {
            tarjetaResponseList.add(TarjetaResponse.builder().token("").nombreTarjeta("").build());
        } else {
            tarjetaList.forEach(tarjeta ->
                    tarjetaResponseList.add(TarjetaResponse.builder()
                            .token(tarjeta.getToken())
                            .nombreTarjeta(tarjeta.getNombre())
                            .build())
            );
        }
        return ListarTarjetaResponse.builder()
                .tarjetaResponseList(tarjetaResponseList).build();

    }
}
