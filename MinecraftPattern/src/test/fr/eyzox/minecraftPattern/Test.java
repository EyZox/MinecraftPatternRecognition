package test.fr.eyzox.minecraftPattern;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.eyzox.minecraftPattern.exception.SyntaxException;
import fr.eyzox.minecraftPattern.io.MCPatternReader;
import fr.eyzox.minecraftPattern.io.PtrnReader;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock;

public class Test {

	public static void main(String[] args) {
		try {
			MCPatternReader reader = new PtrnReader(new File("test.ptrn"));
			MCPatternBlock b;
			while((b = reader.read()) != null) {
				System.out.println(b.toString());
			}
			System.out.println();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
