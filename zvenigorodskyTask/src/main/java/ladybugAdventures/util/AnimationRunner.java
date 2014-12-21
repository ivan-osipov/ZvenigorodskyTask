package ladybugAdventures.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ladybugAdventures.Application;
import ladybugAdventures.entities.GameField;
import ladybugAdventures.entities.ManagementProgram;
import ladybugAdventures.ui.animation.MPViewer;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class AnimationRunner {
	public static void run(final GameField field, final ManagementProgram program){
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(new Runnable() {

			@Override
			public void run() {
				Path resourcePath = null;
				
//				URL resourceUrl = AnimationRunner.class.
//						getResource("/native/win32");
//				try {
					System.out.println(CommonUtils.getJarPath());
					resourcePath = Paths.get(CommonUtils.getJarPath()+"/lib/native/win32/");
//					resourcePath = Paths.get(resourceUrl.toURI());
					System.out.println(resourcePath);
//				} catch (URISyntaxException e1) {
//				}
				System.setProperty("org.lwjgl.librarypath", resourcePath.toString());
				System.setProperty("java.library.path", resourcePath.toString());
				try {
					AppGameContainer container = new AppGameContainer(new MPViewer(field,program));
					container.setDisplayMode(800, 600, false);
					container.setShowFPS(false);
					container.setTitle("Приключения божьей коровки");
					container.setTargetFrameRate(60);
					container.start();
					
				} catch (SlickException e3) {
					Shell shell = new Shell(Display.getDefault());
					MessageDialog.openError(shell, "Ошибка!", "Невозможно отобразить визуализацию.");
					
//					shell.dispose();
				}
				catch(UnsatisfiedLinkError e){
					Shell shell = new Shell(Display.getDefault());
					MessageDialog.openError(shell, "Ошибка!", "Ваша операционная система скорее всего не совместима\n"
							+ " со средством визуализации. \nОбновите библиотеки jinput(/lib/native/win32) в соотсветствии с вашей ОС");
				}
			}
		});
	}
}