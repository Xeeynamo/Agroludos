package agroludos;

import agroludos.db.AgroController;
import agroludos.db.components.*;
import agroludos.db.exception.*;
import agroludos.db.user.*;
import agroludos.gui.*;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FrontController
{
    /**
     * Descrive una coppia tra una richiesta ed una lista di utenti la quale
     * gli è permesso di eseguirla.
     */
    static class Pair
    {
        Request r;
        UserType[] t;
        
        /**
         * Crea una coppia
         * @param r tipo di richiesta
         * @param t lista di utenti che possono eseguire tale richiesta
         */
        public Pair(Request r, UserType[] t)
        {
            this.r = r;
            this.t = t;
        }
        
        public Request getRequest()
        {
            return r;
        }
        
        /**
         * Verifica se la richiesta può essere apportata da un determinato utente
         * @param type tipo di utente che deve eseguire la richiesta
         * @return true se la richiesta è permessa, altrimenti false
         */
        public boolean validateRequest(UserType type)
        {
            for (UserType _type : t)
            {
                if (type == _type)
                    return true;
            }
            return false;
        }
    }
    
    /**
     * Tipologie di utenti possibili
     */
    enum UserType
    {
        Anonimo,
        Partecipante,
        ManagerCompetizione,
        ManagerSistema
    }
    
    /**
     * Richieste possibili
     */
    public enum Request
    {
        Initialize,
        Login,
        AddPartecipante,
        SendMail,
        SendMailToSys,
        
        GetCompetizioniDisponibili,
        GetCompetizioniPrenotate,
        AnnullaPrenotazione,
        AddIscrizioneCompetizione,
        GetCompetizioneFromId,
        IsOptionalSelezionato,
        SetOptionalPrenotazione,
        GetOptional,
        SetOptional,
        GetPartecipante,
        
        FrameLogin,
        FrameRegistrazione,
        FrameHome,
        FrameManagerCompetizione,
        FrameCreaCompetizione,
        FrameManagerSistema,
    }
    private static final Pair[] pair =
    {
        new Pair(Request.Initialize, new UserType[]{UserType.Anonimo}),
        new Pair(Request.Login, new UserType[]{UserType.Anonimo}),
        new Pair(Request.AddPartecipante, new UserType[]{UserType.Anonimo}),
        new Pair(Request.SendMail, new UserType[]{UserType.Anonimo, UserType.Partecipante, UserType.ManagerCompetizione, UserType.ManagerSistema}),
        new Pair(Request.SendMailToSys, new UserType[]{UserType.Partecipante, UserType.ManagerCompetizione}),
        
        new Pair(Request.GetCompetizioniDisponibili, new UserType[]{UserType.Partecipante}),
        new Pair(Request.GetCompetizioniPrenotate, new UserType[]{UserType.Partecipante}),
        new Pair(Request.AnnullaPrenotazione, new UserType[]{UserType.Partecipante}),
        new Pair(Request.AddIscrizioneCompetizione, new UserType[]{UserType.Partecipante}),
        new Pair(Request.GetCompetizioneFromId, new UserType[]{UserType.Partecipante}),
        new Pair(Request.IsOptionalSelezionato, new UserType[]{UserType.Partecipante}),
        new Pair(Request.SetOptionalPrenotazione, new UserType[]{UserType.Partecipante}),
        new Pair(Request.GetOptional, new UserType[]{UserType.ManagerCompetizione, UserType.ManagerSistema}),
        new Pair(Request.SetOptional, new UserType[]{UserType.ManagerSistema}),        new Pair(Request.SetOptionalPrenotazione, new UserType[]{UserType.Partecipante}),
        new Pair(Request.GetPartecipante, new UserType[]{UserType.Partecipante, UserType.ManagerCompetizione, UserType.ManagerSistema}),
        
        new Pair(Request.FrameLogin, new UserType[]{UserType.Anonimo}),
        new Pair(Request.FrameRegistrazione, new UserType[]{UserType.Anonimo}),
        new Pair(Request.FrameHome, new UserType[]{UserType.Partecipante}),
        new Pair(Request.FrameManagerCompetizione, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.FrameCreaCompetizione, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.FrameManagerSistema, new UserType[]{UserType.ManagerSistema}),
    };
    
    AgroController user;
    
    UserType type;
    JFrame currentFrame;
    
    
    public FrontController()
    {
        currentFrame = null;
        type = UserType.Anonimo;
    }
    
    private boolean validateRequest(Request request) throws RequestNotSupportedException
    {
        for (Pair p : pair)
        {
            if (p.getRequest() == request)
                return p.validateRequest(type);
        }
        throw new RequestNotSupportedException();
    }
    
    public Object[] processRequest(Request request, Object ... param) throws DeniedRequestException, RequestNotSupportedException, InternalErrorException
    {
        if (!validateRequest(request))
            throw new DeniedRequestException();
        try
        {
            switch (request)
            {
                case Initialize:
                    user = new Anonimo((String)param[0], (String)param[1], (String)param[2]);
                    break;
                case Login:
                    user = user.Login((String)param[0], (String)param[1]);
                    if (user instanceof agroludos.db.user.Utente)
                    {
                        type = UserType.Partecipante;
                        processRequest(Request.FrameHome, (Object[])null);
                    }
                    else if (user instanceof ManagerCompetizione)
                    {
                        type = UserType.ManagerCompetizione;
                        processRequest(Request.FrameManagerCompetizione, (Object[])null);
                    }
                    else if (user instanceof ManagerSistema)
                    {
                        type = UserType.ManagerSistema;
                        processRequest(Request.FrameManagerSistema, (Object[])null);
                    }
                    break;
                case AddPartecipante:
                    user.addPartecipante((String)param[0], (Partecipante)param[1]);
                    break;
                case SendMail:
                    user.sendMail((String)param[0], (String)param[1], (String)param[2]);
                    break;
                case SendMailToSys:
                    user.sendMail(user.getSysMail(), (String)param[0], (String)param[1]);
                    break;
                    
                case GetCompetizioniDisponibili:
                    return user.getCompetizioniDisponibili();
                case GetCompetizioniPrenotate:
                    return user.getCompetizioniPrenotate();
                case AnnullaPrenotazione:
                    if (user instanceof agroludos.db.user.Utente)
                        user.annullaPrenotazione
                            ((Partecipante)processRequest(Request.GetPartecipante,
                                new Object[] { user.getMail() })[0],
                                (Competizione)param[0]);
                    break;
                case GetPartecipante:
                    return new Object[]
                    {
                        user.getPartecipante((String)param[0])
                    };
                case AddIscrizioneCompetizione:
                    user.addIscrizioneCompetizione(
                            (Partecipante)processRequest(Request.GetPartecipante,
                                    new Object[] { user.getMail() })[0],
                            (Competizione)param[0],
                            (Optional[])param[1]);
                    break;
                case GetCompetizioneFromId:
                    return new Object[]
                    {
                        user.getCompetizione((int)param[0])
                    };
                case IsOptionalSelezionato:
                    if (user instanceof agroludos.db.user.Utente)
                        return new Object[]
                        {
                            user.isOptionalPrenotato(
                                (Partecipante)processRequest(Request.GetPartecipante,
                                        new Object[] { user.getMail() })[0],
                                (Optional)param[0],
                                (Competizione)param[1])
                        };
                case SetOptionalPrenotazione:
                    if (user instanceof agroludos.db.user.Utente)
                        user.setOptionalPrenotazione(
                                (Optional)param[0],
                                (Partecipante)processRequest(Request.GetPartecipante,
                                        new Object[] { user.getMail() })[0],
                                (Competizione)param[1],
                                (boolean)param[2]);
                    break;
                case GetOptional:
                    return user.getOptional();
                case SetOptional:
                    user.setOptional((Optional)param[0]);
                    break;
                    
                case FrameLogin:
                    if (currentFrame != null)
                    {
                        currentFrame.setVisible(false);
                        currentFrame.dispose();
                    }
                    currentFrame = new JFrameLogin(this);
                    currentFrame.setVisible(true);
                    break;
                case FrameRegistrazione:
                    if (currentFrame != null)
                    {
                        currentFrame.setVisible(false);
                        currentFrame.dispose();
                    }
                    currentFrame = new JFrameRegistrazione(this);
                    currentFrame.setVisible(true);
                    break;
                case FrameHome:
                    if (currentFrame != null)
                    {
                        currentFrame.setVisible(false);
                        currentFrame.dispose();
                    }
                    currentFrame = new JFrameHomePartec(this);
                    currentFrame.setVisible(true);
                    break;
                case FrameManagerCompetizione:
                    if (currentFrame != null)
                    {
                        currentFrame.setVisible(false);
                        currentFrame.dispose();
                    }
                    //currentFrame = new JFrameManComp(this);
                    currentFrame.setVisible(true);
                    break;
                case FrameCreaCompetizione:
                    if (currentFrame != null)
                    {
                        currentFrame.setVisible(false);
                        currentFrame.dispose();
                    }
                    //currentFrame = new JFrameCreaComp(this);
                    currentFrame.setVisible(true);
                    break;
                case FrameManagerSistema:
                    if (currentFrame != null)
                    {
                        currentFrame.setVisible(false);
                        currentFrame.dispose();
                    }
                    //currentFrame = new JFrameMainSystem(this);
                    currentFrame.setVisible(true);
                    break;
                   
            }
        }
        catch (MessagingException ex)
        {
            Shared.showError(currentFrame, "Impossibile invire la mail.\n" + ex.toString());
        }
        catch (WrongLoginException | CampiVuotiException |
                DefCodFiscException | DefEmailException | SrcScadutaException ex)
        {
            throw new InternalErrorException(ex.toString());
        }
        catch (ClassNotFoundException ex)
        {
            
        }
        catch (IllegalAccessException ex)
        {
            
        }
        catch (InstantiationException ex)
        {
            
        }
        catch (SQLException ex)
        {
            throw new InternalErrorException("Impossibile stabilire una connessione col database.\n" +
                    ex.toString());
        }
        return null;
    }
}
