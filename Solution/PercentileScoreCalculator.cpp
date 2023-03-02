#include <bits/stdc++.h>
using namespace std;

float calculatePercentile(int totalStudent, vector<int> &marks, int yourScore)
{
    int X = 0;

    for (int i = 0; i < marks.size(); i++)
    {
        if (marks[i] < yourScore)
        {
            X++;
        }
    }

    float ans = (X / (float)totalStudent) * 100;

    return ans;
}

int main()
{
    int totalStudents = 7;
    vector<int> marks = {800, 300, 950, 760, 680, 490, 640};
    int yourScore = 760;
    float percentileScore = calculatePercentile(totalStudents, marks, yourScore);
    cout << percentileScore << endl;
    return 0;
}