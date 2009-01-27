/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.conf
 * File: Configuration.java
 *
 * Property of Leonards / Mindpool
 * Created on 05/05/2004
 */
package leonards.common.log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import leonards.common.conf.ConfigurationSet;

/**
 * @author Mariano
 *
 * This class is the abstraction 
 */
public class FileLogger extends Logger {
    private String path = null;
    private String fileName = null;
    private String rotationDateFormat = null;

    protected final static String DAILY_ROTATION_DATE_FORMAT = "yyyy.MM.dd";
    protected final static String MONTHLY_ROTATION_DATE_FORMAT = "yyyy.MM";
    protected final static String YEARLY_ROTATION_DATE_FORMAT = "yyyy";

    /**
     * 
     */
    public FileLogger() {
        super();
    }

    /**
     * @param conf
     * @throws LogException
     */
    public FileLogger(ConfigurationSet conf) throws LogException {
        super(conf);
    }

    /**
     * @param conf
     * @throws LogException 
     * @see leonards.common.log.Logger#setup(java.lang.String)
     */
    public void setup(ConfigurationSet conf) throws LogException {
		setFileName(conf.getString("filename"));
		setPath(conf.getString("path"));
		setRotationDateFormat(parseRotationValue(conf.getString("rotation")));
    }

    /**
     * 
     * @param paramValue
     * @return
     */
    private String parseRotationValue(String paramValue) {
        if(paramValue != null && paramValue.equalsIgnoreCase("day")) {
            return DAILY_ROTATION_DATE_FORMAT;
        } else if(paramValue != null && paramValue.equalsIgnoreCase("month")) {
            return MONTHLY_ROTATION_DATE_FORMAT;
        } else if(paramValue != null && paramValue.equalsIgnoreCase("year")) {
            return YEARLY_ROTATION_DATE_FORMAT;
        } else {
            return null;
        }
    }
    
    public static void main(String args[]) {
        try {
        Logger l = LoggerFactory.createLogger("default");
        l.logCritical("Mensaje Critico");
        l.logError("Mensaje Error");
        l.logDebug("Mensaje Debug");
        l.logWarning("Mensaje Warning");
        l.logInfo("Mensaje Info");
        } catch(Throwable ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * @param level
     * @param message
     * @param context
     * @throws LogException 
     * @see leonards.common.log.Logger#log(int, java.lang.String, java.lang.Object)
     */
    protected void log(int level, String message, Object context) throws LogException {
    	FileOutputStream logFileStream = null;
    	String fileName = getCurrentFileName();
    	try {
    		logFileStream = new FileOutputStream( fileName, true );
    		logFileStream.write( (getFormattedDate() + " ").getBytes() );
			logFileStream.write( ("[" + getLevelDescription(level) + "] ").getBytes() );
    		if(context != null) {
    		    logFileStream.write( ("Ctx Info: [" + context.toString() + "]").getBytes() );    
    		}
    		logFileStream.write( message.getBytes() );
    		logFileStream.write( "\n".getBytes() );
    		logFileStream.flush();
    	} catch(IOException ex) {
    	    throw new LogException("Could not log to file [" + fileName + "]", ex);
    	} finally {
    	    if( logFileStream != null ) {
    	        try {
    	            logFileStream.close();
    	        } catch( Exception ex ) {
    	            handleException(ex);
    	        }
    	    }		
    	}
    }

    /**
     * 
     * @return
     */
    private String getCurrentFileName() {
        String name = getPath() != null ? getPath() : "";
        String fileSeparator = System.getProperty("file.separator");
        
        if(name.length() > 0 && !name.endsWith(fileSeparator)) {
            name += fileSeparator;
        }
        
        name += getFileName();
        
        if(getRotationDateFormat() != null) {
            SimpleDateFormat df = new SimpleDateFormat(getRotationDateFormat());
            name += "-" + df.format(new Date());
        }
        
        return name + ".log";
    }
    
    /**
     * @return Returns the rotationDateFormat.
     */
    public String getRotationDateFormat() {
        return rotationDateFormat;
    }
    
    /**
     * @param rotationDateFormat The fileCycleDateFormat to set.
     */
    public void setRotationDateFormat(String rotationDateFormat) {
        this.rotationDateFormat = rotationDateFormat;
    }
    
    /**
     * @return Returns the filename.
     */
    public String getFileName() {
        return fileName;
    }
    
    /**
     * @param fileName The file name to set.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * @return Returns the path.
     */
    public String getPath() {
        return path;
    }
    
    /**
     * @param path The path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }
}
