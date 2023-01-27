package club.someoneice.museum.sync;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileHelper {
    public static FileLock FileLockHelper(File file) {
        try {
            if (file.canWrite()) {
                FileChannel fileChannel = new FileOutputStream(file).getChannel();
                return fileChannel.tryLock();
            } else {
                WaitFileUnlock unlockThread = new WaitFileUnlock(file);
                Thread thread = new Thread(unlockThread, "lockFile");
                thread.join();
                return unlockThread.file;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private static final class WaitFileUnlock extends Thread {
        private File getFile;
        public FileLock file = null;

        public WaitFileUnlock(File file) throws IOException {
            getFile = file;
        }

        @Override
        public void run() {
            FileChannel fileChannel;
            try {
                fileChannel = new FileOutputStream(getFile).getChannel();
                this.file = fileChannel.lock();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
