package ccntomcat;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.Date;
import java.util.Random;

import org.ccnx.ccn.CCNHandle;
import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.impl.CCNTrieTree.Vertex;
import org.ccnx.ccn.impl.testHash;
import org.ccnx.ccn.impl.support.Log;
import org.ccnx.ccn.io.CCNFileInputStream;
import org.ccnx.ccn.io.CCNInputStream;
import org.ccnx.ccn.profiles.SegmentationProfile;
import org.ccnx.ccn.protocol.ContentName;
import org.ccnx.ccn.protocol.ContentObject;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;
import org.ccnx.ccn.utils.CommonArguments;
import org.ccnx.ccn.utils.CommonParameters;
import org.ccnx.ccn.utils.Usage;
import org.ccnx.ccn.utils.ccngetfile;
import org.ccnx.ccn.utils.ccnputfile;

/*
 * @author:Yukai Tu
 */
public class CCNGetfileThread implements Runnable {
    //private testHash testhash;
    private ccnputfile ccnpf;
    
    private String[] args = new String[3];
    static Usage u = new ccngetfile();
    //private String ccnfilePath;
  //  int numOfSegment;
   // int lengthOfComponent;
    public CCNGetfileThread(String ccnfilePath,String filetoinsert) {
        //this.testhash = testhash;
        this.args[0] = ccnfilePath;
        this.args[1] = filetoinsert;
      //  this.numOfSegment = numOfSegment;
      //  this.lengthOfComponent = lengthOfComponent;
        //ccnpf = new ccnputfile();
       // ccnpf.setTesthash(testhash);
    }

    public void run() {
    	
    	getfile(this.args);
	}

	public void usage(String extraUsage) {
		System.out.println("usage: ccngetfile " + extraUsage + "[-unversioned] [-timeout millis] [-as pathToKeystore] [-ac (access control)] <ccnname> <filename>");
		System.exit(1);
	}
	public void getfile(String[] args){
		Log.setDefaultLevel(Level.WARNING);

		for (int i = 0; i < args.length; i++) {
			if (!CommonArguments.parseArguments(args, i, u)) {
				System.out.println("getfilethread:"+args[i]);
				if (i >= args.length - 3) {
					CommonParameters.startArg = i;
					break;
				}
				u.usage(CommonArguments.getExtraUsage());
			}
			i = CommonParameters.startArg;
		}

		if (args.length < CommonParameters.startArg + 2) {
			u.usage(CommonArguments.getExtraUsage());
		}

		try {
			int readsize = 1024; // make an argument for testing...
			// If we get one file name, put as the specific name given.
			// If we get more than one, put underneath the first as parent.
			// Ideally want to use newVersion to get latest version. Start
			// with random version.
			//Vertex vertex = testhash.FindPath(args[CommonParameters.startArg]);	
			//return;
			ContentName argName = ContentName.fromURI(args[CommonParameters.startArg]);

			CCNHandle handle = CCNHandle.open();

			File theFile = new File(args[CommonParameters.startArg + 1]);
			if (theFile.exists()) {
				System.out.println("Overwriting file: " + args[CommonParameters.startArg + 1]);
			}
			FileOutputStream output = new FileOutputStream(theFile);

			long starttime = System.currentTimeMillis();
			CCNInputStream input;
			if (CommonParameters.unversioned)
				input = new CCNInputStream(argName, handle);
			else
				input = new CCNFileInputStream(argName, handle);
			if (CommonParameters.timeout != null) {
				input.setTimeout(CommonParameters.timeout);
			}
			byte [] buffer = new byte[readsize];

			int readcount = 0;
			long readtotal = 0;
			//while (!input.eof()) {
			while ((readcount = input.read(buffer)) != -1){
				//readcount = input.read(buffer);
				readtotal += readcount;
				output.write(buffer, 0, readcount);
				output.flush();
			}
			if (CommonParameters.verbose)
				System.out.println("ccngetfile took: "+(System.currentTimeMillis() - starttime)+"ms");
			System.out.println("Retrieved content " + args[CommonParameters.startArg + 1] + " got " + readtotal + " bytes.");
			//System.exit(0);

		} catch (ConfigurationException e) {
			System.out.println("Configuration exception in ccngetfile: " + e.getMessage());
			e.printStackTrace();
		} catch (MalformedContentNameStringException e) {
			System.out.println("Malformed name: " + args[CommonParameters.startArg] + " " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Cannot write file or read content. " + e.getMessage());
			e.printStackTrace();
		}
	}
}
