package com.uplooking.path_files;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Path_FilesDemo {

    /**
     * path-->取代File的
     */
    public static void main1(String[] args) throws IOException, InterruptedException {
        Path path = Paths.get("C:/hello/1.txt");
        Path path2 = Paths.get("C:\\1.txt");
        Path path3 = Paths.get(URI.create("file:///C:/1.txt"));
        Path path4 = FileSystems.getDefault().getPath("C:\\1.txt");

        File file = new File("C:/1.txt");
        Path path5 = file.toPath();
        path5.toFile();

        System.out.println("文件名：" + path.getFileName());
        System.out.println("名称元素的数量：" + path.getNameCount());
        System.out.println("父路径：" + path.getParent());
        System.out.println("根路径：" + path.getRoot());
        System.out.println("是否是绝对路径：" + path.isAbsolute());
        //startsWith()方法的参数既可以是字符串也可以是Path对象
        System.out.println("是否是以为给定的路径D:开始：" + path.startsWith("D:\\"));
        System.out.println("该路径的字符串形式：" + path.toString());
        System.out.println("文件系统：" + path.getFileSystem());
        System.out.println("文件路径是否相同：" + path.compareTo(path2));
        System.out.println("normalize:" + path.normalize());
        System.out.println("绝对路径：" + path.toAbsolutePath());
        System.out.println("真实路径：" + path.toRealPath());

        //根据这条路径合并给定的路径（首尾合并）
        System.out.println("resolve：" + path.resolve(path2));
        //根据此路径的 parent路径解决给定的路径。
        path.resolveSibling("resolveSibling" + path.resolveSibling(path2));
        //构造此路径和给定路径之间的相对路径。
        System.out.println("relativize:" + path.relativize(path2).toString());

        WatchService watcher = FileSystems.getDefault().newWatchService();
        path.register(watcher,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        while (true) {
            WatchKey watchKey = watcher.take(); //这里会进行堵塞
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                System.out.println(event.context() + "--" + event.count() + "--" + event.kind());
            }

            //直接调用WatchKey的reset方法就代表重置其关联的监控器了；
            System.out.println("reset:" + watchKey.reset());
            System.out.println("isValid:" + watchKey.isValid());
        }
    }


    /*=================================Files=========================================*/
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("C:\\mystuff.txt");
        Files.createFile(path);
        Files.createDirectory(path);
        Files.createDirectories(path);//递归创建父目录
        Files.delete(path);

        Path sourcePath = Paths.get("data/logging.properties");
        Path destinationPath = Paths.get("data/logging-copy.properties");
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

        System.out.println(Files.getLastModifiedTime(path));
        System.out.println(Files.size(path));
        System.out.println(Files.isSymbolicLink(path));
        System.out.println(Files.isDirectory(path));
        System.out.println(Files.readAttributes(path, "*"));

        //遍历目录（遍历单个目录，它不会遍历整个目录。遍历整个目录需要使用：）
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path e : stream) {
                System.out.println(e.getFileName());
            }
        } catch (IOException e) {

        }

        //Files.walkFileTree().Files.walkFileTree()方法具有递归遍历目录的功能。

        Stream<Path> walk = Files.walk(path, FileVisitOption.FOLLOW_LINKS);
        Path path1 = Files.walkFileTree(path, new FindJavaVisitor(new ArrayList<>()));
    }


    private static class FindJavaVisitor extends SimpleFileVisitor<Path> {
        private List<Path> result;

        public FindJavaVisitor(List<Path> result) {
            this.result = result;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if (file.toString().endsWith(".java")) {
                result.add(file.getFileName());
            }
            return FileVisitResult.CONTINUE;
        }
    }


}

