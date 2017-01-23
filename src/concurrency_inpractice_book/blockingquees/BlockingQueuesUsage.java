package concurrency_inpractice_book.blockingquees;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

/**
 * Blocking queue usage with poison pills
 * @author Mirna
 *
 */
public class BlockingQueuesUsage {
	private static final int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
	private static final int BOUND = 100;
	private static final Path POISON_PATH = Paths.get("POISON");

	private static class Producer extends SimpleFileVisitor<Path> {
		private BlockingQueue<Path> queue;

		public Producer(BlockingQueue<Path> queue) {
			this.queue = queue;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**{.txt}");
			if (matcher.matches(file)) {
				System.out.println("Visiting File " + file);
				queue.add(file);
			}

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			return super.postVisitDirectory(dir, exc);
		}

	}

	private static class Consumer implements Runnable {
		private BlockingQueue<Path> queue;

		public Consumer(BlockingQueue<Path> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			Path path;

			try {
				while ((path = queue.take()) != POISON_PATH) {
					process(path);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void process(Path file) {
			StringBuilder sb = new StringBuilder(System.lineSeparator());
			try (BufferedReader reader = Files.newBufferedReader(file);) {
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
				}

				System.out.println(String.format("Processed file %s, content%s", file.toString(), sb.toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Path> queue = new ArrayBlockingQueue<>(BOUND);
		List<Path> rootDirs = new ArrayList<>();
		rootDirs.add(Paths.get("C:\\Users\\Mirna\\Desktop\\common-txt"));

		rootDirs.forEach(d -> {
			new Thread(() -> {
				try {
					Files.walkFileTree(d, new Producer(queue));
					
					for (int i = 0; i < N_CONSUMERS; i++) {
						queue.put(POISON_PATH);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}			
			}).start();
		});

		IntStream.range(0, N_CONSUMERS).forEach(i -> {
			new Thread(new Consumer(queue)).start();
		});
	}
}