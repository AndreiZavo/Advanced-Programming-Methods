package View;

import Controller.Controller;
import Exceptions.MyException;
import Model.PrgState;
import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Model.type.*;
import Model.value.BoolValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.IRepo;
import Repo.Repo;
import com.sun.javafx.fxml.expression.VariableExpression;
import com.sun.jdi.IntegerType;
import com.sun.jdi.IntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;


public class Interpreter {


    public static void main(String[] args) throws MyException {
        IStmt example1 = new CompStmt(new VarDeclStmt(new IntType(), "v"),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));

        PrgState prg1 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), example1, new MyLock<>(), new MyLatch<>());
        IRepo repo1 = new Repo(prg1, "log1.txt");
        repo1.addPrg(prg1);
        Controller ctr1 = new Controller(repo1);

        example1.typeCheck(new Dict<>());
        IStmt example2 = new CompStmt( new VarDeclStmt(new IntType(), "a"),
                new CompStmt(new VarDeclStmt(new IntType(), "b"),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new
                                        ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        example2.typeCheck(new Dict<>());
        PrgState prg2 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), example2, new MyLock<>(), new MyLatch<>());
        IRepo repo2 = new Repo(prg2, "log2.txt");
        repo2.addPrg(prg2);
        Controller ctr2 = new Controller(repo2);

        IStmt example3 = new CompStmt(new VarDeclStmt(new BoolType(), "a"),
                new CompStmt(new VarDeclStmt(new IntType(), "v"),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));

        example3.typeCheck(new Dict<>());
        PrgState prg3 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), example3, new MyLock<>(), new MyLatch<>());
        IRepo repo3 = new Repo(prg3, "log3.txt");
        repo3.addPrg(prg3);
        Controller ctr3 = new Controller(repo3);

        IStmt example4 = new CompStmt( new VarDeclStmt(new StringType(), "varf"),
                new CompStmt( new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt( new openRFileStmt(new VarExp("varf")),
                                new CompStmt( new VarDeclStmt(new IntType(), "varc"),
                                        new CompStmt( new readFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt( new PrintStmt(new VarExp("varc")),
                                                        new CompStmt( new readFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt( new PrintStmt(new VarExp("varc")),
                                                                        new closeRFileStmt(new VarExp("varf"))))))))));
        example4.typeCheck(new Dict<>());
        PrgState prg4 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), example4, new MyLock<>(), new MyLatch<>());
        IRepo repo4 = new Repo(prg4, "log4.txt");
        repo4.addPrg(prg4);
        Controller ctr4 = new Controller(repo4);

        IStmt example5 = new CompStmt(new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt( new VarDeclStmt(new RefType(new RefType( new IntType())), "a"),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a")))))));

        example5.typeCheck(new Dict<>());
        PrgState prg5 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), example5, new MyLock<>(), new MyLatch<>());
        IRepo repo5 = new Repo(prg5, "log5.txt");
        repo5.addPrg(prg5);
        Controller ctr5 = new Controller(repo5);

        IStmt example6 = new CompStmt(new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt( new VarDeclStmt(new RefType(new RefType( new IntType())), "a"),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+', new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),new ValueExp(new IntValue(5)))))))));

        example6.typeCheck(new Dict<>());
        PrgState prg6 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), example6, new MyLock<>(), new MyLatch<>());
        IRepo repo6 = new Repo(prg6, "log6.txt");
        repo6.addPrg(prg6);
        Controller ctr6 = new Controller(repo6);

        IStmt example7 = new CompStmt(new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5))))))));

        example7.typeCheck(new Dict<>());
        PrgState prg7 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), example7, new MyLock<>(), new MyLatch<>());
        IRepo repo7 = new Repo(prg7, "log7.txt");
        repo7.addPrg(prg7);
        Controller ctr7 = new Controller(repo7);

        IStmt example8_garbage_collector = new CompStmt(new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt( new VarDeclStmt(new RefType(new RefType( new IntType())), "a"),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));

        example8_garbage_collector.typeCheck(new Dict<>());
        PrgState prg8 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), example8_garbage_collector, new MyLock<>(), new MyLatch<>());
        IRepo repo8 = new Repo(prg8, "log8.txt");
        repo8.addPrg(prg8);
        Controller ctr8 = new Controller(repo8);

        IStmt example9_while = new CompStmt(new VarDeclStmt(new IntType(), "v"),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt( new WhileStmt(new RelationExp(new VarExp("v"), new ValueExp(new IntValue(0)), 5),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        example9_while.typeCheck(new Dict<>());
        PrgState prg9 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), example9_while, new MyLock<>(), new MyLatch<>());
        IRepo repo9 = new Repo(prg9, "log9.txt");
        repo9.addPrg(prg9);
        Controller ctr9 = new Controller(repo9);

        IStmt example10_thread = new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new VarDeclStmt(new RefType(new IntType()), "a"),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));

        example10_thread.typeCheck(new Dict<>());
        PrgState prg10 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), example10_thread, new MyLock<>(), new MyLatch<>());
        IRepo repo10 = new Repo(prg10, "log10.txt");
        repo10.addPrg(prg10);
        Controller ctr10 = new Controller(repo10);

        IStmt exForTypeCheck = new CompStmt(new VarDeclStmt(new IntType(), "a"),
                new CompStmt(new AssignStmt("a", new ValueExp(new StringValue("dada"))),
                        new PrintStmt(new VarExp("a"))));
        // exForTypeCheck.typeCheck(new Dict<>());

        IStmt exFor = new CompStmt(
                new VarDeclStmt(
                        new RefType(new IntType()),
                        "a"
                ),
                new CompStmt(
                        new NewStmt(
                                "a",
                                new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new ForStmt(
                                        new ValueExp(new IntValue(0)),
                                        new ValueExp(new IntValue(3)),
                                        new ArithExp(
                                                '+',
                                                new VarExp("v"),
                                                new ValueExp(new IntValue(1))
                                        ),
                                        new ForkStmt(new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new AssignStmt(
                                                        "v",
                                                        new ArithExp(
                                                                '*',
                                                                new VarExp("v"),
                                                                new ReadHeapExp(new VarExp("a"))
                                                        )
                                                )
                                        ))
                                ),
                                new PrintStmt(new ReadHeapExp(new VarExp("a")))
                        )

                )
        );

        exFor.typeCheck(new Dict<>());
        PrgState prg11 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), exFor, new MyLock<>(), new MyLatch<>());
        IRepo repo11 = new Repo(prg11, "log11.txt");
        repo11.addPrg(prg11);
        Controller ctr11 = new Controller(repo11);


        IStmt exCondAssigment = new CompStmt(
                new VarDeclStmt(
                        new RefType(new IntType()),
                        "a"
                ),
                new CompStmt(
                        new VarDeclStmt(
                                new RefType(new IntType()),
                                "b"
                        ),
                        new CompStmt(
                                new VarDeclStmt(
                                        new IntType(),
                                        "v"
                                ),
                                new CompStmt(
                                        new NewStmt(
                                                "a",
                                                new ValueExp(new IntValue(0))
                                        ),
                                        new CompStmt(
                                                new NewStmt(
                                                        "b",
                                                        new ValueExp(new IntValue(0))
                                                ),
                                                new CompStmt(
                                                        new WriteHeapStmt("a",
                                                                new ValueExp(new IntValue(1))
                                                        ),
                                                        new CompStmt(
                                                                new WriteHeapStmt(
                                                                        "b",
                                                                        new ValueExp(new IntValue(2))
                                                                ),
                                                                new CompStmt(
                                                                        new CondAssignStmt(
                                                                                "v",
                                                                                new RelationExp(
                                                                                        new ReadHeapExp(new VarExp("a")),
                                                                                        new ReadHeapExp(new VarExp("b")),
                                                                                        1
                                                                                ),
                                                                                new ValueExp(new IntValue(100)),
                                                                                new ValueExp(new IntValue(200))
                                                                        ),
                                                                         new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new CompStmt(
                                                                                        new CondAssignStmt(
                                                                                                "v",
                                                                                                new RelationExp(
                                                                                                        new ArithExp(
                                                                                                                '-',
                                                                                                                new ReadHeapExp(new VarExp("b")),
                                                                                                                new ValueExp(new IntValue(2))
                                                                                                        ),
                                                                                                        new ReadHeapExp(new VarExp("a")),
                                                                                                        5
                                                                                                ),
                                                                                                new ValueExp(new IntValue(100)),
                                                                                                new ValueExp(new IntValue(200))
                                                                                        ),
                                                                                        new PrintStmt(new VarExp("v"))
                                                                                )
                                                                        )
                                                                )

                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        exCondAssigment.typeCheck(new Dict<>());
        PrgState prg12 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), exCondAssigment, new MyLock<>(), new MyLatch<>());
        IRepo repo12 = new Repo(prg12, "log12.txt");
        repo12.addPrg(prg12);
        Controller ctr12 = new Controller(repo12);

        IStmt exSwitch = new CompStmt(
                new VarDeclStmt(
                        new IntType(),
                        "a"
                ),
                new CompStmt(
                        new VarDeclStmt(
                                new IntType(),
                                "b"
                        ),
                        new CompStmt(
                                new VarDeclStmt(
                                        new IntType(),
                                        "c"
                                ),
                                new CompStmt(
                                        new AssignStmt(
                                                "a",
                                                new ValueExp(new IntValue(1))
                                        ),
                                        new CompStmt(
                                                new AssignStmt(
                                                        "b",
                                                        new ValueExp(new IntValue(2))
                                                ),
                                                new CompStmt(
                                                        new AssignStmt(
                                                                "c",
                                                                new ValueExp(new IntValue(5))
                                                        ),
                                                        new CompStmt(
                                                                new SwitchStmt(
                                                                        new ArithExp(
                                                                                '*',
                                                                                new VarExp("a"),
                                                                                new ValueExp(new IntValue(10))
                                                                        ),
                                                                        new ArithExp(
                                                                                '*',
                                                                                new VarExp("b"),
                                                                                new VarExp("c")
                                                                        ),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("a")),
                                                                                new PrintStmt(new VarExp("b"))
                                                                        ),
                                                                        new ValueExp(new IntValue(10)),
                                                                        new CompStmt(
                                                                                new PrintStmt(new ValueExp(new IntValue(100))),
                                                                                new PrintStmt(new ValueExp(new IntValue(200)))
                                                                        ),
                                                                        new PrintStmt(new ValueExp(new IntValue(300)))
                                                                ),
                                                                new PrintStmt(new ValueExp(new IntValue(300)))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        exSwitch.typeCheck(new Dict<>());
        PrgState prg13 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), exSwitch, new MyLock<>(), new MyLatch<>());
        IRepo repo13 = new Repo(prg13, "log13.txt");
        repo13.addPrg(prg13);
        Controller ctr13 = new Controller(repo13);


        IStmt exSleep = new CompStmt(
                new VarDeclStmt(
                        new IntType(),
                        "v"
                ),
                new CompStmt(
                        new AssignStmt(
                                "v",
                                new ValueExp(new IntValue(10))
                        ),
                        new CompStmt(
                                new ForkStmt(
                                        new CompStmt(
                                                new AssignStmt(
                                                        "v",
                                                        new ArithExp(
                                                                '-',
                                                                new VarExp("v"),
                                                                new ValueExp(new IntValue(1))
                                                        )
                                                ),
                                                new CompStmt(
                                                        new AssignStmt(
                                                                "v",
                                                                new ArithExp(
                                                                        '-',
                                                                        new VarExp("v"),
                                                                        new ValueExp(new IntValue(1))
                                                                )
                                                        ),
                                                        new PrintStmt(new VarExp("v"))
                                                )
                                        )
                                ),
                                new CompStmt(
                                        new SleepStmt(new ValueExp(new IntValue(10))),
                                        new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10))))

                                )
                        )

                )
        );

        exSwitch.typeCheck(new Dict<>());
        PrgState prg14 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), exSleep, new MyLock<>(), new MyLatch<>());
        IRepo repo14 = new Repo(prg14, "log14.txt");
        repo14.addPrg(prg14);
        Controller ctr14 = new Controller(repo14);

        IStmt exRepeatUntil = new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt(
                                "v",
                                new ValueExp(new IntValue(0))
                        ),
                        new CompStmt(
                                new RepeatUntilStmt(
                                        new RelationExp(
                                                new ValueExp(new IntValue(3)),
                                                new VarExp("v"),
                                                3
                                        ),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new PrintStmt(new VarExp("v")),
                                                                new AssignStmt(
                                                                        "v",
                                                                        new ArithExp(
                                                                                '-',
                                                                                new VarExp("v"),
                                                                                new ValueExp(new IntValue(1))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new AssignStmt(
                                                        "v",
                                                        new ArithExp(
                                                                '+',
                                                                new VarExp("v"),
                                                                new ValueExp(new IntValue(1))
                                                        )
                                                )
                                        )
                                ),
                                new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10))))
                        )
                )
        );

        exRepeatUntil.typeCheck(new Dict<>());
        PrgState prg15 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), exRepeatUntil, new MyLock<>(), new MyLatch<>());
        IRepo repo15 = new Repo(prg15, "log15.txt");
        repo15.addPrg(prg15);
        Controller ctr15 = new Controller(repo15);

        IStmt exLock = new CompStmt(
                new VarDeclStmt(
                        new RefType(new IntType()),
                        "v1"
                ),
                new CompStmt(
                        new VarDeclStmt(
                                new RefType(new IntType()),
                                "v2"
                        ),
                        new CompStmt(
                                new VarDeclStmt(new IntType(), "x"),
                                new CompStmt(
                                        new VarDeclStmt(new IntType(), "q"),
                                        new CompStmt(
                                                new NewStmt("v1", new ValueExp(new IntValue(20))),
                                                new CompStmt(
                                                        new NewStmt("v2", new ValueExp(new IntValue(30))),
                                                        new CompStmt(
                                                                new NewLock("x"),
                                                                new CompStmt(
                                                                        new ForkStmt(
                                                                                new CompStmt(
                                                                                        new ForkStmt(
                                                                                                new CompStmt(
                                                                                                        new LockStmt("x"),
                                                                                                        new CompStmt(
                                                                                                                new WriteHeapStmt(
                                                                                                                        "v1",
                                                                                                                        new ArithExp(
                                                                                                                                '-',
                                                                                                                                new ReadHeapExp(new VarExp("v1")),
                                                                                                                                new ValueExp(new IntValue(1))
                                                                                                                        )
                                                                                                                ),
                                                                                                                new UnlockStmt("x")
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompStmt(
                                                                                                new LockStmt("x"),
                                                                                                new CompStmt(
                                                                                                        new WriteHeapStmt(
                                                                                                                "v1",
                                                                                                                new ArithExp(
                                                                                                                        '*',
                                                                                                                        new ReadHeapExp(new VarExp("v1")),
                                                                                                                        new ValueExp(new IntValue(10))
                                                                                                                )
                                                                                                        ),
                                                                                                        new UnlockStmt("x")
                                                                                                )
                                                                                        )
                                                                                )
                                                                        ),
                                                                        new CompStmt(
                                                                                new NewLock("q"),
                                                                                new CompStmt(
                                                                                        new ForkStmt(
                                                                                                new CompStmt(
                                                                                                        new ForkStmt(
                                                                                                                new CompStmt(
                                                                                                                        new LockStmt("q"),
                                                                                                                        new CompStmt(
                                                                                                                                new WriteHeapStmt(
                                                                                                                                        "v2",
                                                                                                                                        new ArithExp(
                                                                                                                                                '+',
                                                                                                                                                new ReadHeapExp(new VarExp("v2")),
                                                                                                                                                new ValueExp(new IntValue(5))
                                                                                                                                        )
                                                                                                                                ),
                                                                                                                                new UnlockStmt("q")
                                                                                                                        )
                                                                                                                )
                                                                                                        ),
                                                                                                        new CompStmt(
                                                                                                                new LockStmt("q"),
                                                                                                                new CompStmt(
                                                                                                                        new WriteHeapStmt(
                                                                                                                                "v2",
                                                                                                                                new ArithExp(
                                                                                                                                        '*',
                                                                                                                                        new ReadHeapExp(new VarExp("v2")),
                                                                                                                                        new ValueExp(new IntValue(10))
                                                                                                                                )
                                                                                                                        ),
                                                                                                                        new UnlockStmt("q")
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompStmt(
                                                                                                new NopStmt(),
                                                                                                new CompStmt(
                                                                                                        new NopStmt(),
                                                                                                        new CompStmt(
                                                                                                                new NopStmt(),
                                                                                                                new CompStmt(
                                                                                                                        new NopStmt(),
                                                                                                                        new CompStmt(
                                                                                                                                new LockStmt("x"),
                                                                                                                                new CompStmt(
                                                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                                                                        new CompStmt(
                                                                                                                                                new UnlockStmt("x"),
                                                                                                                                                new CompStmt(
                                                                                                                                                        new LockStmt("q"),
                                                                                                                                                        new CompStmt(
                                                                                                                                                                new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                                                                                                                                                                new UnlockStmt("q")
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        exLock.typeCheck(new Dict<>());
        PrgState prg16 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), exLock, new MyLock<>(), new MyLatch<>());
        IRepo repo16 = new Repo(prg16, "log16.txt");
        repo16.addPrg(prg16);
        Controller ctr16 = new Controller(repo16);


        IStmt exLatch = new CompStmt(
                new VarDeclStmt(
                        new RefType(new IntType()),
                        "v1"
                ),
                new CompStmt(
                        new VarDeclStmt(
                                new RefType(new IntType()),
                                "v2"
                        ),
                        new CompStmt(
                                new VarDeclStmt(
                                        new RefType(new IntType()),
                                        "v3"
                                ),
                                new CompStmt(
                                        new VarDeclStmt(
                                                new IntType(),
                                                "cnt"
                                        ),
                                        new CompStmt(
                                                new NewStmt("v1", new ValueExp(new IntValue(2))),
                                                new CompStmt(
                                                        new NewStmt("v2", new ValueExp(new IntValue(3))),
                                                        new CompStmt(
                                                                new NewStmt("v3", new ValueExp(new IntValue(4))),
                                                                new CompStmt(
                                                                        new NewLatchStmt("cnt", new ReadHeapExp(new VarExp("v2"))),
                                                                        new CompStmt(
                                                                                new ForkStmt(
                                                                                        new CompStmt(
                                                                                                new WriteHeapStmt("v1", new ArithExp(
                                                                                                        '*',
                                                                                                        new ReadHeapExp(new VarExp("v1")),
                                                                                                        new ValueExp(new IntValue(10))
                                                                                                )),
                                                                                                new CompStmt(
                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                                        new CompStmt(
                                                                                                                new CountDownStmt("cnt"),
                                                                                                                new ForkStmt(
                                                                                                                        new CompStmt(
                                                                                                                                new WriteHeapStmt("v2", new ArithExp(
                                                                                                                                        '*',
                                                                                                                                        new ReadHeapExp(new VarExp("v2")),
                                                                                                                                        new ValueExp(new IntValue(10))
                                                                                                                                )),
                                                                                                                                new CompStmt(
                                                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                                                                                                                                        new CompStmt(
                                                                                                                                                new CountDownStmt("cnt"),
                                                                                                                                                new ForkStmt(
                                                                                                                                                        new CompStmt(
                                                                                                                                                                new WriteHeapStmt("v3", new ArithExp(
                                                                                                                                                                        '*',
                                                                                                                                                                        new ReadHeapExp(new VarExp("v3")),
                                                                                                                                                                        new ValueExp(new IntValue(10))
                                                                                                                                                                )),
                                                                                                                                                                new CompStmt(
                                                                                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v3"))),
                                                                                                                                                                        new CountDownStmt("cnt")
                                                                                                                                                                )
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompStmt(
                                                                                        new AwaitStmt("cnt"),
                                                                                        new CompStmt(
                                                                                                new PrintStmt(new ValueExp(new IntValue(100))),
                                                                                                new CompStmt(
                                                                                                        new CountDownStmt("cnt"),
                                                                                                        new PrintStmt(new ValueExp(new IntValue(100)))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )


                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        exLatch.typeCheck(new Dict<>());
        PrgState prg17 = new PrgState(new MyStack<>(), new Dict<>(), new Dict<>(), new MyHeap<>(), new MyList<>(), exLatch, new MyLock<>(), new MyLatch<>());
        IRepo repo17 = new Repo(prg17, "log17.txt");
        repo17.addPrg(prg17);
        Controller ctr17 = new Controller(repo17);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", example1.toString(), ctr1));
        menu.addCommand(new RunExample("2", example2.toString(), ctr2));
        menu.addCommand(new RunExample("3", example3.toString(), ctr3));
        menu.addCommand(new RunExample("4", example4.toString(), ctr4));
        menu.addCommand(new RunExample("5", example5.toString(), ctr5));
        menu.addCommand(new RunExample("6", example6.toString(), ctr6));
        menu.addCommand(new RunExample("7", example7.toString(), ctr7));
        menu.addCommand(new RunExample("8", example8_garbage_collector.toString(), ctr8));
        menu.addCommand(new RunExample("9", example9_while.toString(), ctr9));
        menu.addCommand(new RunExample("10", example10_thread.toString(), ctr10));
        menu.addCommand(new RunExample("11", exFor.toString(), ctr11));
        menu.addCommand(new RunExample("12", exCondAssigment.toString(), ctr12));
        menu.addCommand(new RunExample("13", exSwitch.toString(), ctr13));
        menu.addCommand(new RunExample("14", exSleep.toString(), ctr14));
        menu.addCommand(new RunExample("15", exRepeatUntil.toString(), ctr15));
        menu.addCommand(new RunExample("16", exLock.toString(), ctr16));
        menu.addCommand(new RunExample("17", exLatch.toString(), ctr17));

        menu.show();
    }



}
