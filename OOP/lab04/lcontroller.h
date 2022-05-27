#ifndef LCONTROLLER_H
#define LCONTROLLER_H

#include <QObject>
#include "common.h"
#include "ldoors.h"

class lcontroller: public QObject
{
    Q_OBJECT
public:
    explicit lcontroller(QObject *parent = 0);
    enum ControllerState
    {
        IN_PROCESS,
        FREE
    };
    void set_text_edit(QTextEdit *t);
    void set_new_target(int floor);

public slots:
    void pass_floor(int floor, Direction d);
    void achive_floor(int floor);
private slots:

signals:
    void set_target(int floor);
    void next(int floor);

private:
    bool find_next(int &floor);
    bool targets[FLOORS_NUMBERS];
    int cur_floor;
    Direction cur_d;
    ControllerState state;

    QTextEdit *text;
};

#endif // LCONTROLLER_H
