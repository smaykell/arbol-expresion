package arbolgrafico;

import java.util.*;
import javax.swing.JPanel;

public class ArbolExpresion {

	private Nodo raiz;
	private Stack<Nodo> pOperandos;
	private Stack<String> pOperadores;
	private final String blanco;
	private final String operadores;
	private String prefija;
	private String infija;
	private String postfija;

	public ArbolExpresion() {
		this.blanco = " \t";
		this.operadores = ")+-*/%^(";
		this.pOperandos = new Stack<Nodo>();
		this.pOperadores = new Stack<String>();
	}

	public Nodo getRaiz() {
		return this.raiz;
	}

	public void setRaiz(Nodo r) {
		this.raiz = r;
	}

	public boolean contruir(String expresion) {
		construirArbol(expresion);
		return true;
	}

	public Nodo construirArbol(String expresion) {

		StringTokenizer tokenizer;
		String token;

		this.prefija = "";
		this.infija = "";
		this.postfija = "";

		tokenizer = new StringTokenizer(expresion, blanco + operadores, true);
		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			if (blanco.indexOf(token) >= 0) {
			} else if (operadores.indexOf(token) < 0) {
				pOperandos.push(new Nodo(token));
			} else if (token.equals(")")) {
				while (!pOperadores.empty() && !pOperadores.peek().equals("(")) {
					guardarSubArbol();
				}
				pOperadores.pop();
			} else {
				if (!token.equals("(") && !pOperadores.empty()) {
					// operador diferente de cualquier parentesis
					String op = (String) pOperadores.peek();
					while (!op.equals("(") && !pOperadores.empty()
							&& operadores.indexOf(op) >= operadores.indexOf(token)) {
						guardarSubArbol();
						if (!pOperadores.empty())
							op = (String) pOperadores.peek();
					}
				}
				pOperadores.push(token);
			}
		}

		this.raiz = (Nodo) pOperandos.peek();

		while (!pOperadores.empty()) {
			if (pOperadores.peek().equals("(")) {
				pOperadores.pop();
			} else {
				guardarSubArbol();
				this.raiz = (Nodo) pOperandos.peek();
			}
		}

		imprimePre(this.raiz);
		imprimeIn(this.raiz);
		imprimePos(this.raiz);

		return raiz;
	}

	private void guardarSubArbol() {
		Nodo op2 = (Nodo) pOperandos.pop();
		Nodo op1 = (Nodo) pOperandos.pop();
		pOperandos.push(new Nodo(op2, pOperadores.pop(), op1));
	}

	private void imprimeIn(Nodo n) {
		if (n != null) {
			imprimeIn(n.getNodoIzquierdo());
			this.infija += n.getInformacion() + " ";
			imprimeIn(n.getNodoDerecho());
		}
	}

	private void imprimePos(Nodo n) {
		if (n != null) {
			imprimePos(n.getNodoIzquierdo());
			imprimePos(n.getNodoDerecho());
			postfija += n.getInformacion() + " ";
		}
	}

	private void imprimePre(Nodo n) {
		if (n != null) {
			prefija += n.getInformacion() + " ";
			imprimePre(n.getNodoIzquierdo());
			imprimePre(n.getNodoDerecho());
		}
	}

	public JPanel getdibujo() {
		return new ArbolExpresionGrafico(this);
	}

	public String getPrefija() {
		return prefija;
	}

	public String getInfija() {
		return infija;
	}

	public String getPostfija() {
		return postfija;
	}

}
