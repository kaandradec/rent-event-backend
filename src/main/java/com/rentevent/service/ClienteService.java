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

    /**
     * Actualiza el teléfono y el prefijo de un cliente específico.
     * Este método busca un cliente por su correo electrónico y actualiza su teléfono y prefijo con la información proporcionada.
     * Si el cliente no se encuentra, se lanza una excepción.
     *
     * @param clienteTelefonoRequest Los datos de teléfono y prefijo a actualizar para el cliente.
     * @throws NotFoundException Si el cliente con el correo especificado no se encuentra.
     */
    @Transactional
    public void actualizarClienteTelefono(ClienteTelefonoRequest clienteTelefonoRequest) {
        Cliente temp = this.clienteRepository.findByCorreo(clienteTelefonoRequest.getCorreo()).orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        temp.setTelefono(clienteTelefonoRequest.getTelefono());
        temp.setPrefijo(clienteTelefonoRequest.getPrefijo());
        clienteRepository.save(temp);
    }

    /**
     * Actualiza la región y el país de un cliente específico.
     * Este método busca un cliente por su correo electrónico y actualiza su país y región con la información proporcionada.
     * Si el cliente no se encuentra, se lanza una excepción.
     *
     * @param clienteRegionRequest Los datos de región y país a actualizar para el cliente.
     * @throws NotFoundException Si el cliente con el correo especificado no se encuentra.
     */
    @Transactional
    public void actualizarClienteRegion(ClienteRegionRequest clienteRegionRequest) {
        Cliente temp = this.clienteRepository.findByCorreo(clienteRegionRequest.getCorreo()).orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        temp.setPais(clienteRegionRequest.getPais());
        temp.setRegion(clienteRegionRequest.getRegion());
        clienteRepository.save(temp);
    }

    /**
     * Registra una nueva tarjeta de crédito o débito para un cliente específico.
     * Este método busca un cliente por su correo electrónico y asocia una nueva tarjeta a su cuenta.
     * La tarjeta se crea con los datos proporcionados en el request, incluyendo el número de tarjeta,
     * fecha de expiración, nombre en la tarjeta y el tipo de tarjeta. Se genera un token único para la tarjeta
     * basado en los dígitos de la misma. Si el cliente no tiene tarjetas previamente, se inicializa una nueva lista.
     * Finalmente, se guarda la información actualizada del cliente en la base de datos.
     *
     * @param request Los detalles de la tarjeta a registrar, incluyendo el correo del cliente, número de tarjeta,
     *                fecha de expiración, nombre en la tarjeta y tipo de tarjeta.
     * @throws NotFoundException Si el cliente con el correo especificado no se encuentra.
     */
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

    /**
     * Obtiene los datos de facturación de un cliente específico por su correo electrónico.
     * Este método busca un cliente por su correo electrónico y recupera sus datos de facturación.
     * Si el cliente no tiene datos de facturación, se devuelve una respuesta con campos vacíos.
     * De lo contrario, se devuelve una respuesta con los datos de facturación del cliente.
     *
     * @param usuario El correo electrónico del cliente cuyos datos de facturación se desean obtener.
     * @return DatosFacturacionResponse que contiene los datos de facturación del cliente o campos vacíos si no existen.
     * @throws NotFoundException Si el cliente con el correo especificado no se encuentra.
     */
    public DatosFacturacionResponse obtenerDatosFacturacion(String usuario) {
        Cliente cliente = clienteRepository.findByCorreo(usuario).orElseThrow();
        DatosFacturacion datosFacturacion = cliente.getDatosFacturacion();
        if (datosFacturacion == null) {
            return DatosFacturacionResponse.builder().cedula("").direccion("").nombre("").build();
        } else {
            return DatosFacturacionResponse.builder().cedula(datosFacturacion.getCedulaCliente()).direccion(datosFacturacion.getDireccionCliente()).nombre(datosFacturacion.getNombreCliente()).build();
        }
    }

    /**
     * Actualiza los datos de facturación de un cliente específico.
     * Este método busca un cliente por su correo electrónico y actualiza sus datos de facturación con la información proporcionada.
     * Si el cliente no tiene datos de facturación previos, se crean nuevos datos de facturación.
     * Los datos de facturación actualizados o creados se guardan en la base de datos.
     *
     * @param request Los datos de facturación a actualizar o crear, incluyendo cédula, dirección y nombre del cliente.
     * @throws NotFoundException Si el cliente con el correo especificado no se encuentra.
     */
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

    /**
     * Obtiene todas las tarjetas asociadas a un cliente específico por su correo electrónico.
     * Este método busca un cliente por su correo electrónico y recupera todas las tarjetas asociadas a este cliente.
     * Si el cliente tiene tarjetas, se construye una lista de respuestas con los detalles de cada tarjeta.
     * En caso de que el cliente no tenga tarjetas, se devuelve una lista con un elemento vacío.
     *
     * @param usuario El correo electrónico del cliente cuyas tarjetas se desean obtener.
     * @return ListarTarjetaResponse que contiene la lista de tarjetas del cliente.
     * @throws NotFoundException Si el cliente con el correo especificado no se encuentra.
     */
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
