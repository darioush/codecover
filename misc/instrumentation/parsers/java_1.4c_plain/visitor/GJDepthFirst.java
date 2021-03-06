//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJDepthFirst<R,A> implements GJVisitor<R,A> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
   public R visit(NodeList n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n, A argu) {
      if ( n.present() ) {
         R _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n, A argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

   public R visit(NodeSequence n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n, A argu) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * <PRE>
    * f0 -> [ PackageDeclaration() ]
    * f1 -> ( ImportDeclaration() )*
    * f2 -> ( TypeDeclaration() )*
    * f3 -> &lt;EOF&gt;
    * </PRE>
    */
   public R visit(CompilationUnit n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "package"
    * f1 -> Name()
    * f2 -> ";"
    * </PRE>
    */
   public R visit(PackageDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "import"
    * f1 -> Name()
    * f2 -> [ "." "*" ]
    * f3 -> ";"
    * </PRE>
    */
   public R visit(ImportDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ClassDeclaration()
    *       | InterfaceDeclaration()
    *       | ";"
    * </PRE>
    */
   public R visit(TypeDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ( "abstract" | "final" | "public" | "strictfp" )*
    * f1 -> UnmodifiedClassDeclaration()
    * </PRE>
    */
   public R visit(ClassDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "class"
    * f1 -> &lt;IDENTIFIER&gt;
    * f2 -> [ "extends" Name() ]
    * f3 -> [ "implements" NameList() ]
    * f4 -> ClassBody()
    * </PRE>
    */
   public R visit(UnmodifiedClassDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "{"
    * f1 -> ( ClassBodyDeclaration() )*
    * f2 -> "}"
    * </PRE>
    */
   public R visit(ClassBody n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ( "static" | "abstract" | "final" | "public" | "protected" | "private" | "strictfp" )*
    * f1 -> UnmodifiedClassDeclaration()
    * </PRE>
    */
   public R visit(NestedClassDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> Initializer()
    *       | NestedClassDeclaration()
    *       | NestedInterfaceDeclaration()
    *       | ConstructorDeclaration()
    *       | MethodDeclaration()
    *       | FieldDeclaration()
    *       | ";"
    * </PRE>
    */
   public R visit(ClassBodyDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ( "public" | "protected" | "private" | "static" | "abstract" | "final" | "native" | "synchronized" | "strictfp" )*
    * f1 -> ResultType()
    * f2 -> &lt;IDENTIFIER&gt;
    * f3 -> "("
    * </PRE>
    */
   public R visit(MethodDeclarationLookahead n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ( "abstract" | "public" | "strictfp" )*
    * f1 -> UnmodifiedInterfaceDeclaration()
    * </PRE>
    */
   public R visit(InterfaceDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ( "static" | "abstract" | "final" | "public" | "protected" | "private" | "strictfp" )*
    * f1 -> UnmodifiedInterfaceDeclaration()
    * </PRE>
    */
   public R visit(NestedInterfaceDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "interface"
    * f1 -> &lt;IDENTIFIER&gt;
    * f2 -> [ "extends" NameList() ]
    * f3 -> "{"
    * f4 -> ( InterfaceMemberDeclaration() )*
    * f5 -> "}"
    * </PRE>
    */
   public R visit(UnmodifiedInterfaceDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> NestedClassDeclaration()
    *       | NestedInterfaceDeclaration()
    *       | MethodDeclaration()
    *       | FieldDeclaration()
    *       | ";"
    * </PRE>
    */
   public R visit(InterfaceMemberDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ( "public" | "protected" | "private" | "static" | "final" | "transient" | "volatile" )*
    * f1 -> Type()
    * f2 -> VariableDeclarator()
    * f3 -> ( "," VariableDeclarator() )*
    * f4 -> ";"
    * </PRE>
    */
   public R visit(FieldDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> VariableDeclaratorId()
    * f1 -> [ "=" VariableInitializer() ]
    * </PRE>
    */
   public R visit(VariableDeclarator n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> &lt;IDENTIFIER&gt;
    * f1 -> ( "[" "]" )*
    * </PRE>
    */
   public R visit(VariableDeclaratorId n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ArrayInitializer()
    *       | Expression()
    * </PRE>
    */
   public R visit(VariableInitializer n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "{"
    * f1 -> [ VariableInitializer() ( "," VariableInitializer() )* ]
    * f2 -> [ "," ]
    * f3 -> "}"
    * </PRE>
    */
   public R visit(ArrayInitializer n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ( "public" | "protected" | "private" | "static" | "abstract" | "final" | "native" | "synchronized" | "strictfp" )*
    * f1 -> ResultType()
    * f2 -> MethodDeclarator()
    * f3 -> [ "throws" NameList() ]
    * f4 -> ( Block() | ";" )
    * </PRE>
    */
   public R visit(MethodDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> &lt;IDENTIFIER&gt;
    * f1 -> FormalParameters()
    * f2 -> ( "[" "]" )*
    * </PRE>
    */
   public R visit(MethodDeclarator n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "("
    * f1 -> [ FormalParameter() ( "," FormalParameter() )* ]
    * f2 -> ")"
    * </PRE>
    */
   public R visit(FormalParameters n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> [ "final" ]
    * f1 -> Type()
    * f2 -> VariableDeclaratorId()
    * </PRE>
    */
   public R visit(FormalParameter n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> [ "public" | "protected" | "private" ]
    * f1 -> &lt;IDENTIFIER&gt;
    * f2 -> FormalParameters()
    * f3 -> [ "throws" NameList() ]
    * f4 -> "{"
    * f5 -> [ ExplicitConstructorInvocation() ]
    * f6 -> ( BlockStatement() )*
    * f7 -> "}"
    * </PRE>
    */
   public R visit(ConstructorDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "this" Arguments() ";"
    *       | [ PrimaryExpression() "." ] "super" Arguments() ";"
    * </PRE>
    */
   public R visit(ExplicitConstructorInvocation n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> [ "static" ]
    * f1 -> Block()
    * </PRE>
    */
   public R visit(Initializer n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ( PrimitiveType() | Name() )
    * f1 -> ( "[" "]" )*
    * </PRE>
    */
   public R visit(Type n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "boolean"
    *       | "char"
    *       | "byte"
    *       | "short"
    *       | "int"
    *       | "long"
    *       | "float"
    *       | "double"
    * </PRE>
    */
   public R visit(PrimitiveType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "void"
    *       | Type()
    * </PRE>
    */
   public R visit(ResultType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> &lt;IDENTIFIER&gt;
    * f1 -> ( "." &lt;IDENTIFIER&gt; )*
    * </PRE>
    */
   public R visit(Name n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> Name()
    * f1 -> ( "," Name() )*
    * </PRE>
    */
   public R visit(NameList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ConditionalExpression()
    * f1 -> [ AssignmentOperator() Expression() ]
    * </PRE>
    */
   public R visit(Expression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "="
    *       | "*="
    *       | "/="
    *       | "%="
    *       | "+="
    *       | "-="
    *       | "&lt;&lt;="
    *       | "&gt;&gt;="
    *       | "&gt;&gt;&gt;="
    *       | "&="
    *       | "^="
    *       | "|="
    * </PRE>
    */
   public R visit(AssignmentOperator n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ConditionalOrExpression()
    * f1 -> [ "?" Expression() ":" ConditionalExpression() ]
    * </PRE>
    */
   public R visit(ConditionalExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ConditionalAndExpression()
    * f1 -> ( "||" ConditionalAndExpression() )*
    * </PRE>
    */
   public R visit(ConditionalOrExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> InclusiveOrExpression()
    * f1 -> ( "&&" InclusiveOrExpression() )*
    * </PRE>
    */
   public R visit(ConditionalAndExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ExclusiveOrExpression()
    * f1 -> ( "|" ExclusiveOrExpression() )*
    * </PRE>
    */
   public R visit(InclusiveOrExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> AndExpression()
    * f1 -> ( "^" AndExpression() )*
    * </PRE>
    */
   public R visit(ExclusiveOrExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> EqualityExpression()
    * f1 -> ( "&" EqualityExpression() )*
    * </PRE>
    */
   public R visit(AndExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> InstanceOfExpression()
    * f1 -> ( ( "==" | "!=" ) InstanceOfExpression() )*
    * </PRE>
    */
   public R visit(EqualityExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> RelationalExpression()
    * f1 -> [ "instanceof" Type() ]
    * </PRE>
    */
   public R visit(InstanceOfExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ShiftExpression()
    * f1 -> ( ( "&lt;" | "&gt;" | "&lt;=" | "&gt;=" ) ShiftExpression() )*
    * </PRE>
    */
   public R visit(RelationalExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> AdditiveExpression()
    * f1 -> ( ( "&lt;&lt;" | "&gt;&gt;" | "&gt;&gt;&gt;" ) AdditiveExpression() )*
    * </PRE>
    */
   public R visit(ShiftExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> MultiplicativeExpression()
    * f1 -> ( ( "+" | "-" ) MultiplicativeExpression() )*
    * </PRE>
    */
   public R visit(AdditiveExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> UnaryExpression()
    * f1 -> ( ( "*" | "/" | "%" ) UnaryExpression() )*
    * </PRE>
    */
   public R visit(MultiplicativeExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ( "+" | "-" ) UnaryExpression()
    *       | PreIncrementExpression()
    *       | PreDecrementExpression()
    *       | UnaryExpressionNotPlusMinus()
    * </PRE>
    */
   public R visit(UnaryExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "++"
    * f1 -> PrimaryExpression()
    * </PRE>
    */
   public R visit(PreIncrementExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "--"
    * f1 -> PrimaryExpression()
    * </PRE>
    */
   public R visit(PreDecrementExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ( "~" | "!" ) UnaryExpression()
    *       | CastExpression()
    *       | PostfixExpression()
    * </PRE>
    */
   public R visit(UnaryExpressionNotPlusMinus n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "(" PrimitiveType()
    *       | "(" Name() "[" "]"
    *       | "(" Name() ")" ( "~" | "!" | "(" | &lt;IDENTIFIER&gt; | "this" | "super" | "new" | Literal() )
    * </PRE>
    */
   public R visit(CastLookahead n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> PrimaryExpression()
    * f1 -> [ "++" | "--" ]
    * </PRE>
    */
   public R visit(PostfixExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "(" Type() ")" UnaryExpression()
    *       | "(" Type() ")" UnaryExpressionNotPlusMinus()
    * </PRE>
    */
   public R visit(CastExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> PrimaryPrefix()
    * f1 -> ( PrimarySuffix() )*
    * </PRE>
    */
   public R visit(PrimaryExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> Literal()
    *       | "this"
    *       | "super" "." &lt;IDENTIFIER&gt;
    *       | "(" Expression() ")"
    *       | AllocationExpression()
    *       | ResultType() "." "class"
    *       | Name()
    * </PRE>
    */
   public R visit(PrimaryPrefix n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "." "this"
    *       | "." "super"
    *       | "." AllocationExpression()
    *       | "[" Expression() "]"
    *       | "." &lt;IDENTIFIER&gt;
    *       | Arguments()
    * </PRE>
    */
   public R visit(PrimarySuffix n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> &lt;INTEGER_LITERAL&gt;
    *       | &lt;FLOATING_POINT_LITERAL&gt;
    *       | &lt;CHARACTER_LITERAL&gt;
    *       | &lt;STRING_LITERAL&gt;
    *       | BooleanLiteral()
    *       | NullLiteral()
    * </PRE>
    */
   public R visit(Literal n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "true"
    *       | "false"
    * </PRE>
    */
   public R visit(BooleanLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "null"
    * </PRE>
    */
   public R visit(NullLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "("
    * f1 -> [ ArgumentList() ]
    * f2 -> ")"
    * </PRE>
    */
   public R visit(Arguments n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> Expression()
    * f1 -> ( "," Expression() )*
    * </PRE>
    */
   public R visit(ArgumentList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "new" PrimitiveType() ArrayDimsAndInits()
    *       | "new" Name() ( ArrayDimsAndInits() | Arguments() [ ClassBody() ] )
    * </PRE>
    */
   public R visit(AllocationExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ( "[" Expression() "]" )+ ( "[" "]" )*
    *       | ( "[" "]" )+ ArrayInitializer()
    * </PRE>
    */
   public R visit(ArrayDimsAndInits n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> LabeledStatement()
    *       | Block()
    *       | EmptyStatement()
    *       | StatementExpression() ";"
    *       | SwitchStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | DoStatement()
    *       | ForStatement()
    *       | BreakStatement()
    *       | ContinueStatement()
    *       | ReturnStatement()
    *       | ThrowStatement()
    *       | SynchronizedStatement()
    *       | TryStatement()
    *       | AssertStatement()
    * </PRE>
    */
   public R visit(Statement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> &lt;IDENTIFIER&gt;
    * f1 -> ":"
    * f2 -> Statement()
    * </PRE>
    */
   public R visit(LabeledStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "{"
    * f1 -> ( BlockStatement() )*
    * f2 -> "}"
    * </PRE>
    */
   public R visit(Block n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> LocalVariableDeclaration() ";"
    *       | Statement()
    *       | UnmodifiedClassDeclaration()
    *       | UnmodifiedInterfaceDeclaration()
    * </PRE>
    */
   public R visit(BlockStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> [ "final" ]
    * f1 -> Type()
    * f2 -> VariableDeclarator()
    * f3 -> ( "," VariableDeclarator() )*
    * </PRE>
    */
   public R visit(LocalVariableDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> ";"
    * </PRE>
    */
   public R visit(EmptyStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> PreIncrementExpression()
    *       | PreDecrementExpression()
    *       | PrimaryExpression() [ "++" | "--" | AssignmentOperator() Expression() ]
    * </PRE>
    */
   public R visit(StatementExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "switch"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> "{"
    * f5 -> ( SwitchLabel() ( BlockStatement() )* )*
    * f6 -> "}"
    * </PRE>
    */
   public R visit(SwitchStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "case" Expression() ":"
    *       | "default" ":"
    * </PRE>
    */
   public R visit(SwitchLabel n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> [ "else" Statement() ]
    * </PRE>
    */
   public R visit(IfStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * </PRE>
    */
   public R visit(WhileStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "do"
    * f1 -> Statement()
    * f2 -> "while"
    * f3 -> "("
    * f4 -> Expression()
    * f5 -> ")"
    * f6 -> ";"
    * </PRE>
    */
   public R visit(DoStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "for"
    * f1 -> "("
    * f2 -> [ ForInit() ]
    * f3 -> ";"
    * f4 -> [ Expression() ]
    * f5 -> ";"
    * f6 -> [ ForUpdate() ]
    * f7 -> ")"
    * f8 -> Statement()
    * </PRE>
    */
   public R visit(ForStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> LocalVariableDeclaration()
    *       | StatementExpressionList()
    * </PRE>
    */
   public R visit(ForInit n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> StatementExpression()
    * f1 -> ( "," StatementExpression() )*
    * </PRE>
    */
   public R visit(StatementExpressionList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> StatementExpressionList()
    * </PRE>
    */
   public R visit(ForUpdate n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "break"
    * f1 -> [ &lt;IDENTIFIER&gt; ]
    * f2 -> ";"
    * </PRE>
    */
   public R visit(BreakStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "continue"
    * f1 -> [ &lt;IDENTIFIER&gt; ]
    * f2 -> ";"
    * </PRE>
    */
   public R visit(ContinueStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "return"
    * f1 -> [ Expression() ]
    * f2 -> ";"
    * </PRE>
    */
   public R visit(ReturnStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "throw"
    * f1 -> Expression()
    * f2 -> ";"
    * </PRE>
    */
   public R visit(ThrowStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "synchronized"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Block()
    * </PRE>
    */
   public R visit(SynchronizedStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "try"
    * f1 -> Block()
    * f2 -> ( "catch" "(" FormalParameter() ")" Block() )*
    * f3 -> [ "finally" Block() ]
    * </PRE>
    */
   public R visit(TryStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * <PRE>
    * f0 -> "assert"
    * f1 -> Expression()
    * f2 -> [ ":" Expression() ]
    * f3 -> ";"
    * </PRE>
    */
   public R visit(AssertStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

}
