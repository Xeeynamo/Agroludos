/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.server;

import agroludos.components.TransferObject;
import agroludos.server.ApplicationController.Request;
import agroludos.server.exception.DeniedRequestException;
import agroludos.server.exception.InternalErrorException;
import agroludos.server.exception.RequestNotSupportedException;

/**
 *
 * @author Luciano
 */
public class Dispatcher
{
    static class Pair
    {
        String name;
        Request request;
        public Pair(String name, Request request)
        {
            this.name = name;
            this.request = request;
        }
        String getName()
        {
            return name;
        }
        Request getRequest()
        {
            return request;
        }
    }
    private static final Pair[] pair =
    {
        new Pair("Initialize", Request.Initialize),
        new Pair("Login", Request.Login),
        new Pair("AddPartecipante", Request.AddPartecipante),
        new Pair("SendMail", Request.SendMail),
        new Pair("SendMailToSys", Request.SendMailToSys),
        new Pair("GetManagers", Request.GetManagers),
        new Pair("GetCompetizioniDisponibili", Request.GetCompetizioniDisponibili),
        new Pair("GetCompetizioniPrenotate", Request.GetCompetizioniPrenotate),
        new Pair("GetCompetizione", Request.GetCompetizione),
        new Pair("GetCompetizioneFromId", Request.GetCompetizioneFromId),
        new Pair("GetCompetizioni", Request.GetCompetizioni),
        new Pair("GetCompetizioniMinimal", Request.GetCompetizioniMinimal),
        new Pair("AnnullaPrenotazione", Request.AnnullaPrenotazione),
        new Pair("AddIscrizioneCompetizione", Request.AddIscrizioneCompetizione),
        new Pair("IsOptionalSelezionato", Request.IsOptionalSelezionato),
        new Pair("SetOptionalPrenotazione", Request.SetOptionalPrenotazione),
        new Pair("GetOptional", Request.GetOptional),
        new Pair("SetOptional", Request.SetOptional),
        new Pair("SetOptionalPrenotazione", Request.SetOptionalPrenotazione),
        new Pair("GetPartecipante", Request.GetPartecipante),
        new Pair("GetPartecipanti", Request.GetPartecipanti),
        new Pair("GetPartecipantiCompetizione", Request.GetPartecipantiCompetizione),
        new Pair("GetPartecipantiMinimal", Request.GetPartecipantiMinimal),
        new Pair("GetPartecipanteCompetizioni", Request.GetPartecipanteCompetizioni),
        new Pair("GetCompetizioneTipi", Request.GetCompetizioneTipi),
        new Pair("AnnullaCompetizione", Request.AnnullaCompetizione),
        new Pair("AddCompetizione", Request.AddCompetizione),
        new Pair("IsModificaScaduto", Request.isModificaScaduto),
        new Pair("SetNPartMax", Request.setNPartMax),
        new Pair("SetNPartMin", Request.setNPartMin),
        new Pair("SetPrezzoComp", Request.setPrezzoComp),
        new Pair("SetOptionalCompetizione", Request.setOptionalCompetizione),
        new Pair("DropOptionalCompetizione", Request.dropOptionalCompetizione),
        new Pair("GetOptional", Request.getOptional),
        new Pair("FrameLogin", Request.FrameLogin),
        new Pair("FrameRegistrazione", Request.FrameRegistrazione),
        new Pair("FrameHome", Request.FrameHome),
        new Pair("FrameManagerCompetizione", Request.FrameManagerCompetizione),
        new Pair("FrameCreaCompetizione", Request.FrameCreaCompetizione),
        new Pair("FrameManagerSistema", Request.FrameManagerSistema),
    };
    
    private final ApplicationController appCtrl;
    public Dispatcher(String server, String username, String password) throws InternalErrorException
    {
        appCtrl = new ApplicationController(server, username, password);
    }
    public void dispatch(String request)
    {
        for (int i = 0; i < pair.length; i++)
        {
            if (request.compareTo(pair[i].getName()) == 0)
                appCtrl.processRequest(pair[i].getRequest(), null);
        }
    }
}
