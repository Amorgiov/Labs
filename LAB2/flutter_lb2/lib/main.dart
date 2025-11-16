import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});
  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(widget.title)),
      body: CustomScrollView(
        slivers: [
          const ListWidget(divider: 2),

          SliverToBoxAdapter(
            child: Padding(
              padding: const EdgeInsets.all(16),
              child: Text(
                "Второй список ↓",
                style: Theme.of(context).textTheme.headlineSmall,
              ),
            ),
          ),

          // Второй список
          const ListWidget(divider: 5),
        ],
      )
    );
  }
}

class ListWidget extends StatefulWidget {
  final int divider;

  const ListWidget({super.key, this.divider = 1});

  @override
  State<ListWidget> createState() => _ListWidgetState();
}

class _ListWidgetState extends State<ListWidget> {
  late final List<ListItem> items;
  
  @override
  void initState() {
    super.initState();
    
    items = List<ListItem>.generate(10, (index) {
      if (index % widget.divider == 0) {
        return HeadingItem("Заголовок $index");
      }
      return MessageItem(
          title: "Рядовой элемент $index",
          description: "Описание $index");
    });
  }
  
  @override
  Widget build(BuildContext context) {
    return SliverList(
        delegate: SliverChildBuilderDelegate(
                (context, index) {
                  final item = items[index];
                    if (item is HeadingItem) {
                      return ListTile(
                        key: ValueKey<int>(index),
                        title: Text(item.title ?? "",
                            style: Theme.of(context).textTheme.headlineSmall),
                        leading: const Icon(Icons.check_circle_rounded),
                      );
                    } else if (item is MessageItem) {
                      return ListTile(
                        key: ValueKey<int>(index),
                        title: Text(item.title ?? ""),
                        subtitle: Text(item.description),
                      );
                    }
                    return null;
                  },
                  childCount: items.length,
                  findChildIndexCallback: (key) {
                    if (key is ValueKey<int>) {
                      return key.value;
                    }
                    return null;
            }));
  }
}

abstract class ListItem{
  final String? title;

  ListItem(this.title);
}

class HeadingItem extends ListItem{
  HeadingItem(String super.title);
}

class MessageItem extends ListItem{
  final String description;

  MessageItem({required String title, this.description = ""}) : super(title);
}


