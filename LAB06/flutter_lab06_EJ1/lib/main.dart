import 'package:flutter/material.dart';

void main() => runApp(const AppEdad());

class AppEdad extends StatelessWidget {
  const AppEdad({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Calculadora de Edad',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        useMaterial3: true,
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.indigo),
      ),
      home: const PantallaEdad(),
    );
  }
}

class PantallaEdad extends StatefulWidget {
  const PantallaEdad({super.key});

  @override
  State<PantallaEdad> createState() => _PantallaEdadState();
}

class _PantallaEdadState extends State<PantallaEdad> {
  final TextEditingController _controller = TextEditingController();
  int? _edad;
  String? _error;

  void _calcular() {
    final texto = _controller.text.trim();
    final anio = int.tryParse(texto);
    final anioActual = DateTime.now().year;

    setState(() {
      if (texto.isEmpty) {
        _error = 'El campo no puede estar vacío';
        _edad = null;
      } else if (anio == null) {
        _error = 'Ingresa un número válido';
        _edad = null;
      } else if (anio < 1900 || anio > anioActual) {
        _error = 'El año debe estar entre 1900 y $anioActual';
        _edad = null;
      } else {
        _error = null;
        _edad = anioActual - anio;
      }
    });
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Calculadora de Edad'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [

            // Campo de entrada
            TextField(
              controller: _controller,
              keyboardType: TextInputType.number,
              maxLength: 4,
              decoration: InputDecoration(
                border: const OutlineInputBorder(),
                labelText: 'Año de nacimiento',
                errorText: _error,
                prefixIcon: const Icon(Icons.cake),
              ),
            ),

            const SizedBox(height: 16),

            // Botón
            FilledButton.icon(
              onPressed: _calcular,
              icon: const Icon(Icons.calculate),
              label: const Text('Calcular edad'),
            ),

            const SizedBox(height: 24),

            // Resultado
            if (_edad != null)
              Card(
                elevation: 4,
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Text(
                    'Tu edad es de $_edad años',
                    style: Theme.of(context).textTheme.headlineSmall,
                    textAlign: TextAlign.center,
                  ),
                ),
              ),
          ],
        ),
      ),
    );
  }
}