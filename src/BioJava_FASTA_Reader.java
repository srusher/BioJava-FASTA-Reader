/*
Class Name: BIFS 618
Homework assignment #5, question #2
File name: hwk5_2.java
Program author name: Samuel Rusher
*/

import org.biojava.bio.BioException;
import org.biojava.bio.seq.DNATools;
import org.biojava.bio.symbol.SymbolList;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.bio.seq.RichSequenceIterator;
import org.biojavax.bio.seq.RichSequence.IOTools;

import java.io.*;
import javax.swing.JFileChooser;

public class BioJava_FASTA_Reader {

    private static JFileChooser ourChooser = new JFileChooser(".");


    public static BufferedReader openFile(){ //method that utilizes the JFileChooser class to generate a GUI and select the appropriate file
        int retval = ourChooser.showOpenDialog(null);
        BufferedReader br = null;

        if (retval == JFileChooser.APPROVE_OPTION){
            File file = ourChooser.getSelectedFile();
            try {
                br = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                System.out.println("trouble reading "+file.getName());
                e.printStackTrace();
            }
        }
        return br;
    }


    public static void main(String[] args) throws BioException{
        BufferedReader br = openFile();

        RichSequenceIterator it =  IOTools.readFastaDNA(br, null);

        int count = 0;

        while (it.hasNext()){
            count++;
            RichSequence s = it.nextRichSequence();
            System.out.println("\n\n\n"+s.getAccession()); //inserted 3 new lines for readability
            System.out.println( s.seqString()); //printing original dna sequence

            System.out.println("\nReading Frame #1:"); //printing Reading Frame 1 message
            SymbolList p = DNATools.toProtein(s); //converting the RichSequence 's' object into a protein sequence using the DNATools toProtein method
            String p2 = p.seqString(); //converting the protein Symbol List into a string for printing
            for (int i=0;i<p2.length();i++){ //iterating the length of the protein string p2
            System.out.print(p2.charAt(i)+"  "); //printing each amino acid in the protein string p2 followed by 2 spaces so each printed amino acid aligns with the first nucleotide of the codon in the dna sequence above it
            }

            System.out.println("\nReading Frame #2:"); //printing Reading Frame 1 message -- same process as RF 1 but I'm creating a sub list of the Rich Sequence 's'
            p = DNATools.toProtein(s.subList(2,s.length())); //creating a sublist of 's' that excludes the first nucleotide of the dna seq
            p2 = p.seqString();
            for (int i=0;i<p2.length();i++){
                System.out.print(p2.charAt(i)+"  ");
            }

            System.out.println("\nReading Frame #3:");
            p = DNATools.toProtein(s.subList(3,s.length())); //creating a sublist of 's' that excludes the first AND second nucleotides of the dna seq
            p2 = p.seqString();
            for (int i=0;i<p2.length();i++){
                System.out.print(p2.charAt(i)+"  ");
            }

        }
        System.out.println("# sequences read: "+count);
    }
}
