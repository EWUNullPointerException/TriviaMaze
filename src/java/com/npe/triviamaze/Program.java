package com.npe.triviamaze;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class Program
{
    private static final int WIDTH = 361;
    private static final int HEIGHT = 348;
    private static Shell shell;

    public static void main(String[] args)
    {
        Display display = new Display();
        shell = new Shell(display);

        // Dan
        // must be called before setSize is called to make sure
        // the components are placed correctly for some odd reason.
        // This makes it almost impossible to set anything relative
        // to the size of the window so I've moved width and height
        // to finals
        init();
        shell.setSize(WIDTH, HEIGHT);
        shell.setLayout(new FormLayout());

        shell.open();
        // run the event loop as long as the window is open
        while(!shell.isDisposed())
        {
            // read the next OS event queue and transfer it to a SWT event
            if(!display.readAndDispatch())
            {
                // if there are currently no other OS event to process
                // sleep until the next OS event is available
                display.sleep();
            }
        }

        // disposes all associated windows and their components
        display.dispose();
    }

    private static void init()
    {
        // Dan

        Button exitBtn = new Button(shell, SWT.PUSH);
        FormData fd_exitBtn = new FormData();
        fd_exitBtn.top = new FormAttachment(0);
        fd_exitBtn.left = new FormAttachment(0);
        exitBtn.setLayoutData(fd_exitBtn);

        // Adds functionality to the button by adding a listener for
        // being clicked, or as SWT has defined it, for "selection"
        exitBtn.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                // Not sure if this conditional is needed as it seems
                // to work as expected without it.
                // if(event.type == SWT.Selection)
                shell.dispose();
            }
        });
        exitBtn.setText("Exit");
        // Resize the control before moving it.
        exitBtn.pack();

    }
}
