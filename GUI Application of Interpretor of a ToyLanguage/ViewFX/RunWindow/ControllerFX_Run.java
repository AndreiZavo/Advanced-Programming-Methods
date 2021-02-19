package ViewFX.RunWindow;

import Controller.Controller;
import Exceptions.MyException;
import Model.PrgState;
import Model.adt.Dict;
import Model.adt.MyHeap;
import Model.adt.MyList;
import Model.adt.MyStack;
import Model.exp.*;
import Model.stmt.*;
import Model.type.*;
import Model.value.BoolValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.IRepo;
import Repo.Repo;
import View.Interpreter;
import View.TextMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerFX_Run implements Initializable {

    @FXML
    private ListView<Controller> exerciseListView;

    @FXML
    private Button showButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exerciseListView.setItems(getControllerList());
        exerciseListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void handleShowButtonAction() throws Exception {

        Controller controller = exerciseListView.getSelectionModel().getSelectedItem();
        if(controller == null)
            AlertBox.display("Error", "Please select one valid program to run from the list");
        else {
            try {
                controller.typecheckOriginalProgram();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("main.fxml"));
                ControllerFX_Main controllerFX_main = new ControllerFX_Main(controller);
                loader.setController(controllerFX_main);
                BorderPane mainRoot = (BorderPane) loader.load();
                Scene mainScene = new Scene(mainRoot, 805, 430);
                String css = getClass().getResource("mainStyle.css").toExternalForm();
                mainScene.getStylesheets().add(css);
                Stage mainStage = new Stage();
                mainStage.setTitle("Visual Debugger");
                mainStage.setScene(mainScene);
                mainStage.show();

            } catch (MyException e) {
                AlertBox.display("Error", "Please select one valid program to run from the list");
            }

            closeCurrentStage();
        }
    }

    private void closeCurrentStage() {
        Stage stage = (Stage) showButton.getScene().getWindow();
        stage.close();
    }

    public MyList<IStmt> getStatementList() {
        IStmt example1 = new CompStmt(new VarDeclStmt(new IntType(), "v"),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));

        IStmt example2 = new CompStmt(new VarDeclStmt(new IntType(), "a"),
                new CompStmt(new VarDeclStmt(new IntType(), "b"),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new
                                ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new
                                        ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));


        IStmt example3 = new CompStmt(new VarDeclStmt(new BoolType(), "a"),
                new CompStmt(new VarDeclStmt(new IntType(), "v"),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));


        IStmt example4 = new CompStmt(new VarDeclStmt(new StringType(), "varf"),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new openRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt(new IntType(), "varc"),
                                        new CompStmt(new readFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new readFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new closeRFileStmt(new VarExp("varf"))))))))));

        IStmt example5 = new CompStmt(new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a")))))));

        IStmt example6 = new CompStmt(new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+', new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));

        IStmt example7 = new CompStmt(new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5))))))));

        IStmt example8_garbage_collector = new CompStmt(new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));

        IStmt example9_while = new CompStmt(new VarDeclStmt(new IntType(), "v"),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationExp(new VarExp("v"), new ValueExp(new IntValue(0)), 5),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));


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


        IStmt exForTypeCheck = new CompStmt(new VarDeclStmt(new IntType(), "a"),
                new CompStmt(new AssignStmt("a", new ValueExp(new StringValue("dada"))),
                        new PrintStmt(new VarExp("a"))));

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

        MyList<IStmt> statementList = new MyList<>();
        statementList.add(example1);
        statementList.add(example2);
        statementList.add(example3);
        statementList.add(example4);
        statementList.add(example5);
        statementList.add(example6);
        statementList.add(example7);
        statementList.add(example8_garbage_collector);
        statementList.add(example9_while);
        statementList.add(example10_thread);
        statementList.add(exForTypeCheck);
        statementList.add(exFor);
        statementList.add(exCondAssigment);
        statementList.add(exSwitch);
        statementList.add(exSleep);
        statementList.add(exRepeatUntil);
        statementList.add(exLock);
        return statementList;
    }

    private ObservableList<Controller> getControllerList() {
        MyList<IStmt> statements = getStatementList();
        LinkedList<Controller> list = new LinkedList<>();
        for (int i = 0; i < statements.size(); ++i) {
            PrgState prg = PrgState.createEmptyProgramState(statements.get(i));
            IRepo repo = new Repo(prg, "log" + String.valueOf(i + 1) + ".txt");
            Controller controller = new Controller(repo);
            list.add(controller);
        }
        return FXCollections.observableArrayList(list);

    }
}
