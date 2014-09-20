package agroludos.server;

import agroludos.components.*;
import agroludos.exception.*;
import agroludos.gui.*;
import agroludos.server.exception.DeniedRequestException;
import agroludos.server.exception.InternalErrorException;
import agroludos.server.exception.RequestNotSupportedException;
import agroludos.server.lang.LangManager;
import java.sql.SQLException;
import java.util.Date;
import javax.mail.MessagingException;
import javax.swing.JFrame;

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
        /**
         * restituisce la finestra dedicata all'utente appena loggato. Se ci 
         * sono dei dati che non corrispondono ad un particolare utente presente
         * nel sistema o non indicano nessun utente in generale non apre alcuna 
         * finestra
         * 
         * @param String Email; 
         * @param String Password;
         * 
         * @return
         *      Successo: presenta una nuova finestra dedicata in base
         *                 alla tipologia dell'utente loggato (se l'utente è
         *                 un partecipante alle competizioni, allora aprirà la
         *                 finestra per abilitare il partecipante alle funzioni
         *                 a lui desiderate);
         * 
         *@return  
         *      Fallimento: non cambia nulla e rimane nella stessa finestra
         *                   cui si stava operando;
         */
        Login,
        /**
         * Aggiunge un nuovo utente di tipo "Partecipante" nel sistema. In caso
         * però di dati ridondanti (un altro utente con la stessa email o con
         * lo stesso codice fiscale) o in presenza di campi lasciati vuoti
         * nell'atto della registrazione dei dati allora il sistema non
         * aggiungerà il nuovo utente
         * 
         * @param
         *      String Password;
         * @param
         *      Partecipante partecipante da registrare nel sistema
         *
         * @return
         *      Successo: il nuovo utente di tipo "Partecipante" è stato
         *                registrato nel sistema e quindi può accedervi;
         * 
         * @return
         *      Fallimento: il nuovo utente non è stato registrato nel sistema
         *                  quindi non può accedervi
         */
        AddPartecipante,
        SendMail,
        SendMailToSys,
        /**
         * Restituisce tutti i manager di competizioni presenti nel sistema.
         * 
         * @param
         * 
         * @return lista dei Manager di Competizioni presenti nel sistema
         */
        GetManagers,
        /**
         * Restituisce tutte le competizioni a cui un dato partecipante può 
         * prenotarsi
         * 
         * @param
         * 
         * @return lista delle Competizioni disponibili
         */
        GetCompetizioniDisponibili,
        /**
         * Restituisce tutte le competizioni a cui un dato partecipante si è
         * già prenotato e che devono essere ancora svolte
         * 
         * @param
         * 
         * @return lista delle Competizioni prenotate
         */
        GetCompetizioniPrenotate,
        /**
         * Restituisce una data competizione passando come parametro il suo:
         *      <p>-<strong>ID</strong>, oppure;
         *      <p>-la <strong>mail</strong> del Manager competizione e <strong>data</strong>cui dovrebbe svolgersi tale
         *      competizione.
         * 
         * @param int ID;
         * <p>-----------------------------
         * @param String Email Manager Competizione data;
         * @param Date Data
         * svolgimento competizione;
         * 
         * @return la Competizione desiderata, oppure null
         */
        GetCompetizione,
    /**
     * Ottiene una lista di competizioni gestite da uno specifico manager.
     * Le competizioni annullate non saranno incluse nella lista.
     * 
     * @param 
     * String mail del manager di competizione (SOLO se usata da <strong>Manager Sistema</strong>);
     * 
     * @return lista delle competizioni gestite dal manager specificato
     */
        GetCompetizioni,
    /**
     * Ottiene una lista minimale delle competizioni a parte da un filtro
     * @param int è un numero che, con un OR tra bit, crea un filtro:
     * 1 = competizioni in corso
     * 2 = competizioni già passate
     * 4 = competizioni annullate
     * 
     * ESEMPIO: se desiderò cercare tutte le competizioni "in corso" e 
     * anche quelle "annullate" allora passerò come paramentro 1("in corso")+
     * 4("annullate"). se desidero vedere tutte le competizioni presenti nel sistema
     * passerò 7.
     * 
     * @return lista minimale di competizioni quali corrispondono alla richiesta fatta
     * @throws SQLException 
     */
        GetCompetizioniMinimal,
        /**
         * Annulla la prenotazione effettuata da un dato partecipante per
         * una data competizione
         * 
         * 
         * @param
         * Partecipante partecipante cui si desidera annullare la prenotazione
         * (SOLO se usata da <strong>Manager Competizione</strong>);
         * @param
         * Competizione Competizione da cui annullare la prenotazione;
         * 
         * @return
         */
        AnnullaPrenotazione,
        /**
         * Effettua la prenotazione di un partecipante ad una data competizione,
         * tranne nel caso in cui il certificato SRC del suddetto partecipante
         * non sia valida per la data di quella competizione
         * 
         * @param
         * Competizione Competizione su cui effettuare la prenotazione;
         * Optional [] Lista di Optional richiesti dal partecipante
         * 
         * @return
         * Successo: la prenotazione è registrata nel sistema;
         * Fallimento: non viene segnalata alcuna prenotazione;

         */
        AddIscrizioneCompetizione,
        
     /**
     * Restituisce una data competizione tramite il suo ID
     * 
     * @param int ID della competizione da analizzare
     * 
     * @return Competizione associata all'ID
     */
        GetCompetizioneFromId,
        /**
         * Ritorna true se nella prenotazione effettuata da un partecipante
         * ad una data competizione ha indicato di usufruire di un dato 
         * optional, altrimenti false (potrebbe anche significare che il part.
         * non abbia effettuato alcuna prenotazione per tale competizione)
         * 
         * 
         * @param 
         * 
         * Optional l'Optional da controllare se è stato 
         * selezionato o meno;
         * @param
         * Competizione la Competizione su cui è stata effettuata la prenotazione da controllare
         * 
         * @return true,false
         */
        IsOptionalSelezionato,
        /**
         * Data una prenotazione, fa si che un dato optional possa risultare
         * richiesto o meno dal partecipante
         * 
         * @param
         * Optionall'Optional quale si desidera far risultare
         * richiesto/non richiesto dal partecipante;
         * @param
         * Competizione la Competizione su cui è stata effetuata la prenotazione;
         * @param
         * boolean per indicare se tale Optional deve risultare
         * richiesto(true)/non richiesto(false)
         * 
         * 
         */
        SetOptionalPrenotazione,
            /**
     * Ottiene una lista di tutti gli optional presenti nel database
     * @return lista degli optional
     */
        GetOptional,
        /**
         * Modifica del prezzo e/o delle descrizioni di un'optional da
         * effetuare nel sistema
         * 
         *  @param Optional l'Optional modificato
         * 
         * @return
         */
        SetOptional,
    /**
     * Restituisce un  partecipante tramite la sua email
     * 
     * @param String email da analizzare
     * 
     * @return Partecipante associato all'email
     */
        GetPartecipante,
        GetPartecipanti,
        /**
         * Restituisce la lista dei partecipanti che si sono prenotati ad
         * una data competizione
         * 
         * @param int id della competizione da analizzare
         * 
         * @return lista di partecipanti
         */
        GetPartecipantiCompetizione,
    /**
     * Ottiene le informazioni base di un partecipante, quali mail nome e cognome.
     * Questa funzione serve per permettere di restituire una lista di partecipanti,
     * ma senza appesantire troppo le query ed il client. Infatti non vi sarà
     * bisogno di dover caricare tutti i partecipanti insieme, ma solo caricare le
     * informazioni strettamente necessarie per poi entrare nel dettaglio di un
     * partecipante uno alla volta. Si noti che ogni partecipante restituito da
     * questa funzione, presenterà isValid() sempre falso poiché gli altri campi
     * vengono lasciati vuoti. Si noti come la lista sia ordinata per cognome.
     * 
     * @return lista dei partecipanti
     */
        GetPartecipantiMinimal,
    /**
     * Ottiene una lista di id delle competizioni a cui un partecipante si è iscritto
     * 
     * @param String mail email del partecipante 
     * 
     * @return lista di id di competizioni
     *  
     */
        GetPartecipanteCompetizioni,
    /**
     * Ottiene una lista dei vari tipi di competizione presenti
     *
     * @return lista di tipi di competizione
     */
        GetCompetizioneTipi,
        /**
         * Crea e registra una nuova competizione gestita dal manager
         * di competizione che l'ha creato
         * 
         * @param float prezzo;
         * @param int numero minimo di partecipanti;
         * @param int numero massimo di
         * partecipanti;
         * @param TipoCompetizione tipologia di competizione;
         * @param date data svolgimento;
         * @param Optional []lista
         * degli optional messi a disposizione per la competizione;
         */
        AddCompetizione,
        /**
         * Annulla la competizione desiderata, facendola risultare al sistema,
         * appunto, come annullata
         * 
         * @param int ID della competizione da annullare
         */
        AnnullaCompetizione,
        /**
         * Indica se ad una data competizione si possano ancora effettuare
         * o meno modifiche
         * 
         * @param Competizione la competizione interessata
         * 
         * @result true/false
         */
        isModificaScaduto,
        /**
         * Imposta ad una determinata competizione un nuovo valore quale
         * indicherà il numero massimo di partecipanti quali potranno prenotarsi
         * alla suddetta competizione
         * 
         * @param int ID della competizione desiderata
         * @param int il numero che indicherà la soglia massima dei partecipanti
         * 
         * 
         */
        setNPartMax,
        /**
         * Imposta ad una determinata competizione un nuovo valore quale
         * indicherà il numero minimo di partecipanti è garantita/richiesta
         * la presenza affinchè la competizione possa avere luogo
         * 
         * @param int ID della competizione desiderata
         * @param int il numero che indicherà la soglia minima dei partecipanti
         * 
         * 
         */
        setNPartMin,
        /**
         * Imposta ad una determinata competizione il prezzo per l'iscrizione
         * 
         * @param int ID della competizione desiderata
         * @param float prezzo da impostare
         */
        setPrezzoComp,
        /**
         * Rende disponibile un dato optional ad una data competizione
         * 
         * @param Competizione la competizione su cui si vuole rendere
         * disponibile il dato optional
         * @param Optional l'optional che si vuole mettere a disposizione
         */
        setOptionalCompetizione,
        /**
         * Rende INdisponibile un dato optional per una data competizione
         * 
         * @param Competizione la competizione su cui si vuole rendere
         * INdisponibile il dato optional
         * @param Optional l'optional da rendere INdisponibile
         */
        dropOptionalCompetizione,
        /**
         * Restituisce tutte le informazioni relative all'optional desiderato
         * 
         * @param String nome dell'optional desiderato
         * 
         * @result L'optional desiderato
         */
        getOptional,
        /**
         * Visualizza la finestra del Login
         */
        FrameLogin,
        /**
         * Visualizza la finestra della registrazione
         */
        FrameRegistrazione,
        /**
         * Visualizza la finestra dedicata all'utente di tipo "Partecipante"
         */
        FrameHome,
        /**
         * Visualizza la finestra dedicata all'utente di tipo "Manager
         * Competizioni"
         */
        FrameManagerCompetizione,
        /**
         * Visualizza la finestra dedicata alla creazione di una competizione
         * da parte di un utente di tipo "Manager Competizioni"
         */
        FrameCreaCompetizione,
        /**
         * Visualizza la finestra dedicata all'utente di tipo "Manager
         * Sistema"
         */
        FrameManagerSistema,
    }
    private static final Pair[] pair =
    {
        new Pair(Request.Initialize, new UserType[]{UserType.Anonimo}),
        new Pair(Request.Login, new UserType[]{UserType.Anonimo}),
        new Pair(Request.AddPartecipante, new UserType[]{UserType.Anonimo}),
        new Pair(Request.SendMail, new UserType[]{UserType.Anonimo, UserType.Partecipante, UserType.ManagerCompetizione, UserType.ManagerSistema}),
        new Pair(Request.SendMailToSys, new UserType[]{UserType.Partecipante, UserType.ManagerCompetizione}),
        
        new Pair(Request.GetManagers, new UserType[]{UserType.ManagerSistema}),
        new Pair(Request.GetCompetizioniDisponibili, new UserType[]{UserType.Partecipante}),
        new Pair(Request.GetCompetizioniPrenotate, new UserType[]{UserType.Partecipante}),
        new Pair(Request.GetCompetizione, new UserType[]{UserType.ManagerSistema}),
        new Pair(Request.GetCompetizioneFromId, new UserType[]{UserType.Partecipante,UserType.ManagerCompetizione, UserType.ManagerSistema}),
        new Pair(Request.GetCompetizioni, new UserType[]{UserType.ManagerCompetizione, UserType.ManagerSistema}),
        new Pair(Request.GetCompetizioniMinimal, new UserType[]{UserType.ManagerSistema}),
        new Pair(Request.AnnullaPrenotazione, new UserType[]{UserType.Partecipante,UserType.ManagerCompetizione}),
        new Pair(Request.AddIscrizioneCompetizione, new UserType[]{UserType.Partecipante}),
        new Pair(Request.IsOptionalSelezionato, new UserType[]{UserType.Partecipante}),
        new Pair(Request.SetOptionalPrenotazione, new UserType[]{UserType.Partecipante}),
        new Pair(Request.GetOptional, new UserType[]{UserType.ManagerCompetizione, UserType.ManagerSistema}),
        new Pair(Request.SetOptional, new UserType[]{UserType.ManagerSistema}),        new Pair(Request.SetOptionalPrenotazione, new UserType[]{UserType.Partecipante}),
        new Pair(Request.GetPartecipante, new UserType[]{UserType.Partecipante, UserType.ManagerCompetizione, UserType.ManagerSistema}),
        new Pair(Request.GetPartecipanti, new UserType[]{UserType.ManagerSistema}),
        new Pair(Request.GetPartecipantiCompetizione, new UserType[]{UserType.ManagerCompetizione, UserType.ManagerSistema}),
        new Pair(Request.GetPartecipantiMinimal, new UserType[]{UserType.ManagerSistema}),
        new Pair(Request.GetPartecipanteCompetizioni, new UserType[]{UserType.ManagerSistema}),
        new Pair(Request.GetCompetizioneTipi, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.AnnullaCompetizione, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.AddCompetizione, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.isModificaScaduto, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.setNPartMax, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.setNPartMin, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.setPrezzoComp, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.setOptionalCompetizione, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.dropOptionalCompetizione, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.getOptional, new UserType[]{UserType.ManagerCompetizione}),
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
    
    public Object[] processRequest(Request request, TransferObject o) throws DeniedRequestException, RequestNotSupportedException, InternalErrorException
    {
        if (!validateRequest(request))
            throw new DeniedRequestException();
        try
        {
            switch (request)
            {
                case Initialize:
                    user = new AgroController(o.getIndex(0).toString(), o.getIndex(1).toString(), o.getIndex(2).toString());
                    break;
                case Login:
                    user = user.Login(o.getIndex(0).toString(), o.getIndex(1).toString());
                    switch (user.getType())
                    {
                        case Partecipante:
                            type = UserType.Partecipante;
                            processRequest(Request.FrameHome, null);
                            break;
                        case ManagerCompetizione:
                            type = UserType.ManagerCompetizione;
                            processRequest(Request.FrameManagerCompetizione, null);
                            break;
                        case ManagerSistema:
                            type = UserType.ManagerSistema;
                            processRequest(Request.FrameManagerSistema, null);
                            break;
                    }
                    break;
                case AddPartecipante:
                    user.addPartecipante(o.getIndex(0).toString(), (Partecipante)o.getIndex(1));
                    break;
                case SendMail:
                    user.sendMail(o.getIndex(0).toString(), o.getIndex(1).toString(), o.getIndex(2).toString());
                    break;
                case SendMailToSys:
                    user.sendMail(user.getSysMail(), o.getIndex(0).toString(), o.getIndex(1).toString());
                    break;
                    
                case GetManagers:
                    return user.getManagers();
                case GetCompetizioniDisponibili:
                    return user.getCompetizioniDisponibili();
                case GetCompetizioniPrenotate:
                    return user.getCompetizioniPrenotate();
                case GetCompetizione:
                    return new Object[]
                    {
                        user.getCompetizione(o.getIndex(1).toValue())
                    };
                case GetCompetizioni:
                    if (type == UserType.ManagerCompetizione)
                        return user.getCompetizioni(user.getMail());
                    else if (type == UserType.ManagerSistema)
                        return user.getCompetizioni(o.getIndex(0).toString());
                    break;
                case GetCompetizioniMinimal:
                    return user.getCompetizioniMinimal(o.getIndex(0).toValue());
                case AnnullaPrenotazione:
                    if (type == UserType.Partecipante)
                    {
                        TransferObject to = new TransferObject(user.getMail());
                        Partecipante p = (Partecipante)processRequest(Request.GetPartecipante, to)[0];
                        user.annullaPrenotazione(p, (Competizione)o.getIndex(0));
                    }
                    else if (type == UserType.ManagerCompetizione)
                    {
                        user.annullaPrenotazione((Partecipante)o.getIndex(0), (Competizione)o.getIndex(1));
                    }
                    break;
                case GetPartecipante:
                    return new Object[]
                    {
                        user.getPartecipante(o.getIndex(0).toString())
                    };
                case GetPartecipanti:
                    return user.getPartecipanti(o.getIndex(0).toValue());
                case AddIscrizioneCompetizione:
                {
                    TransferObject to = new TransferObject(user.getMail());
                    Partecipante p = (Partecipante)processRequest(Request.GetPartecipante, to)[0];
                    TransferObject optList = (TransferObject)o.getIndex(1);
                    user.addIscrizioneCompetizione(p, (Competizione)o.getIndex(0), (Optional[])optList.getArray());
                }
                    break;
                case GetCompetizioneFromId:
                    return new Object[]
                    {
                        user.getCompetizione(o.getIndex(0).toValue())
                    };
                case IsOptionalSelezionato:
                {
                    TransferObject to = new TransferObject(user.getMail());
                    Partecipante p = (Partecipante)processRequest(Request.GetPartecipante, to)[0];
                    
                    return new Object[]
                    {
                        user.isOptionalPrenotato(p, (Optional)o.getIndex(0), (Competizione)o.getIndex(1))
                    };
                }
                case SetOptionalPrenotazione:
                {
                    TransferObject to = new TransferObject(user.getMail());
                    Partecipante p = (Partecipante)processRequest(Request.GetPartecipante, to)[0];
                    
                    user.setOptionalPrenotazione(
                            (Optional)o.getIndex(0),
                            p,
                            (Competizione)o.getIndex(1),
                            o.getIndex(2).toValue() == 1);
                }
                    break;
                case GetOptional:
                    return user.getOptional();
                case SetOptional:
                    user.setOptional((Optional)o.getIndex(0));
                    break;
                case GetPartecipantiCompetizione:
                    return user.getPartecipanti(o.getIndex(0).toValue());
                case GetPartecipantiMinimal:
                    return user.getPartecipantiMinimal();
                case GetPartecipanteCompetizioni:
                    return user.getPartecipanteCompetizioni(o.getIndex(0).toString());
                case GetCompetizioneTipi:
                    return user.getCompetizioneTipi();
                case AddCompetizione:
                {
                    Manager manager = new Manager("", "", user.getMail());
                    Competizione c = (Competizione)o.getIndex(0);
                    Competizione cc = new Competizione(0, c.getPrezzo(), c.getNMin(), c.getNMax(),
                            0, c.getTipoCompetizione(), manager, c.getDataComp(), c.getOptional());
                    user.creaCompetizione(cc);
                }
                    break;
                case AnnullaCompetizione:
                    user.annullaCompetizione(o.getIndex(0).toValue());
                    break;
                case isModificaScaduto:
                    if(user.getNGiorniMancanti(((Competizione)o.getIndex(0)).getDataComp()) < 2)
                        return new Object[]{true};
                    else
                        return new Object[]{false};
                case setNPartMax:
                    user.setNPartMax(o.getIndex(0).toValue(), o.getIndex(1).toValue());
                    break;
                case setNPartMin:
                    user.setNPartMin(o.getIndex(0).toValue(), o.getIndex(1).toValue());
                    break;
                case setPrezzoComp:
                    user.setPrezzoComp(o.getIndex(0).toValue(), o.getIndex(1).toValueF());
                    break;
                case setOptionalCompetizione:
                    return new Object[]{
                    user.setOptionalCompetizione((Competizione)o.getIndex(0), (Optional)o.getIndex(1))};
                case dropOptionalCompetizione:
                    return new Object[]{
                    user.dropOptionalCompetizione((Competizione)o.getIndex(0), (Optional)o.getIndex(0))};
                case getOptional:
                    return new Object [] {user.getOptional(o.getIndex(0).toString())};
                case FrameLogin:
                    user.setCurrentFrame(new JFrameLogin(this));
                    break;
                case FrameRegistrazione:
                    user.setCurrentFrame(new JFrameRegistrazione(this));
                    break;
                case FrameHome:
                    user.setCurrentFrame(new JFrameHomePartec(this));
                    break;
                case FrameManagerCompetizione:
                    user.setCurrentFrame(new JFrameManComp(this));
                    break;
                case FrameCreaCompetizione:
                    user.setCurrentFrame(new JFrameCreaComp(this));
                    break;
                case FrameManagerSistema:
                    user.setCurrentFrame(new JFrameMainSystem(this));
                    break;
                   
            }
        }
        catch (ClassNotFoundException ex)
        {
            
        }
        catch (MessagingException ex)
        {
            Shared.showError(currentFrame, "Impossibile invire la mail.\n" + ex.toString());
        }
        catch (WrongLoginException | CampiVuotiException | TipoCompetizioneInvalidException |
                DefCodFiscException | DefEmailException | SrcScadutaException | CompPienaException
                |MinMaxException | DatePriorException | CompetizioneEsistenteException ex)
        {
            throw new InternalErrorException(ex.toString());
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
