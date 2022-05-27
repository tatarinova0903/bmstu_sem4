#ifndef LCABIN_H
#define LCABIN_H

#include "ldoors.h"
class lcabin : public QObject
{
    Q_OBJECT
public:
    explicit lcabin(QObject *parent = 0);
    enum CabinState
    {
      SET_TARGET,
      MOVING,
      STAY
    };
    void set_text_edit(QTextEdit *t);

public slots:

    void set_target(int floor);
    void cabin_stopping();
    void cabin_moving();

private slots:

signals:

    void passing_floor(int floor, Direction d);
    void pass_target_floor(int floor);
    void cabin_stop();
    void go();

private:
    CabinState state;
    int current_floor;
    int target_floor;

    QTimer one_floor_Timer;

    Direction d;
    ldoors doors;

    QTextEdit *text;
};

#endif // LCABIN_H
