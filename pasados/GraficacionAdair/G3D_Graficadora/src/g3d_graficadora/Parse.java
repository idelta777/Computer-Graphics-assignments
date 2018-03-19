package g3d_graficadora;

import org.nfunk.jep.JEP;

  public class Parse {

        JEP evaluador;
        double vx;
        boolean errorNumero;
        boolean errorExpr;

        public Parse() {

            evaluador = new JEP();
            evaluador.addStandardFunctions();
            evaluador.addStandardConstants();
            evaluador.addComplex();
            evaluador.addFunction("sen", new org.nfunk.jep.function.Sine());
            evaluador.addVariable("x", 0);
            evaluador.addVariable("z", 0);
            evaluador.setImplicitMul(true);
        }

        public double evaluar(JEP parser, String m, double valor) {
            parser.parseExpression(m);
            parser.addVariable("x", valor);
            errorExpr = parser.hasError();
            return parser.getValue();
        }
    }
